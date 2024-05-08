package com.example.socketmarket.domain.repository

import com.example.socketmarket.data.remote.dto.IntradayInfo
import com.example.socketmarket.domain.model.CompanyInfo
import com.example.socketmarket.domain.model.CompanyListing
import com.example.socketmarket.util.Resource
import kotlinx.coroutines.flow.Flow


interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote:Boolean,
        query:String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntradayInfo(
        symbol: String
    ): Resource<List<IntradayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resource<CompanyInfo>

}