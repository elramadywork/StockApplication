package com.example.socketmarket.data.csv

import com.example.socketmarket.domain.model.CompanyListing
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

class CompanyListingParser @Inject constructor() :CSVParser<CompanyListing> {
    override suspend fun parse(stream: InputStream): List<CompanyListing> {
        val csv= CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO){
            csv.readAll()
                .drop(1)
                .mapNotNull {line->
                    val symbol=line.getOrNull(0)
                    val name=line.getOrNull(1)
                    val exchange=line.getOrNull(2)
                    CompanyListing(
                        name=name?: return@mapNotNull null,
                        symbol=symbol?: return@mapNotNull null,
                        exchange=exchange?: return@mapNotNull null,
                    )
                }
                .also {
                    csv.close()
                }
        }
    }
}