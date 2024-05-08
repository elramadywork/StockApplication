package com.example.socketmarket.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface StockDao {

    @Upsert
    suspend fun insertCompanyListings(
        companyListingEntity: List<CompanyListingEntity>
    )


    @Query("DELETE FROM companylistingentity")
    suspend fun clearCompanyListings()


    @Query(
        """
            SELECT *
            FROM companylistingentity
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
            UPPER(:query)==symbol
        """
    )
    suspend fun searchCompanyListing(query:String):List<CompanyListingEntity>




}