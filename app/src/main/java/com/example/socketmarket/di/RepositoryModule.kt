package com.example.socketmarket.di


import com.example.socketmarket.data.csv.CSVParser
import com.example.socketmarket.data.csv.CompanyListingParser
import com.example.socketmarket.data.csv.IntradayInfoParser
import com.example.socketmarket.data.remote.dto.IntradayInfo
import com.example.socketmarket.data.repository.StockRepositoryImpl
import com.example.socketmarket.domain.model.CompanyListing
import com.example.socketmarket.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: CompanyListingParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindIntradayInfoParser(
        intradayInfoParser: IntradayInfoParser
    ): CSVParser<IntradayInfo>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}