package com.example.socketmarket.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.animation.core.rememberTransition
import com.example.socketmarket.data.csv.CSVParser
import com.example.socketmarket.data.csv.CompanyListingParser
import com.example.socketmarket.data.local.StockDatabase
import com.example.socketmarket.data.mapper.CompanyListingEntity
import com.example.socketmarket.data.mapper.toCompanyInfo
import com.example.socketmarket.data.mapper.toCompanyListing
import com.example.socketmarket.data.remote.StockApi
import com.example.socketmarket.data.remote.dto.IntradayInfo
import com.example.socketmarket.domain.model.CompanyInfo
import com.example.socketmarket.domain.model.CompanyListing
import com.example.socketmarket.domain.repository.StockRepository
import com.example.socketmarket.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(

    private  val api:StockApi,
    private  val db:StockDatabase,
    private  val companyListingParser:CSVParser<CompanyListing>,
    private  val intradayInfoParser: CSVParser<IntradayInfo>,

    ):StockRepository {

    private val database=db.dao

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
      return flow{

          emit(Resource.Loading(true))

          val localListings= database.searchCompanyListing(query)

          emit(Resource.Success(
              data = localListings.map {
                  it.toCompanyListing()
              }
          ))

          val isDbEmpty=localListings.isEmpty() && query.isBlank()
          val shouldLoadFromCache=!isDbEmpty && !fetchFromRemote

          if (shouldLoadFromCache){
              emit(Resource.Loading(false))
              return@flow
          }

          val remoteListings=try {
              val response=api.getListings()
              companyListingParser.parse(response.byteStream())
          }catch (e:IOException){
              e.printStackTrace()
              emit(Resource.Error("couldn't load data"))
              null
          }catch (e:HttpException){
              e.printStackTrace()
              emit(Resource.Error("couldn't load data"))
              null
          }

          remoteListings?.let {listings->
              database.clearCompanyListings()
              database.insertCompanyListings(
                  listings.map { it.CompanyListingEntity() }
              )


              emit(Resource.Success(
                  data =
                  database.searchCompanyListing("").map {
                      it.toCompanyListing()
                  }
              ))

              emit(Resource.Loading(false))

          }


        }
    }


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response = api.getIntradayInfo(symbol)
            val results = intradayInfoParser.parse(response.byteStream())
            Resource.Success(results)
        } catch(e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load intraday info"
            )
        } catch(e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load intraday info"
            )
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val result = api.getCompanyInfo(symbol)
            Resource.Success(result.toCompanyInfo())
        } catch(e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load company info"
            )
        } catch(e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load company info"
            )
        }
    }
}