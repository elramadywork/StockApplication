package com.example.socketmarket.data.mapper

import com.example.socketmarket.data.local.CompanyListingEntity
import com.example.socketmarket.data.remote.dto.CompanyInfoDto
import com.example.socketmarket.domain.model.CompanyInfo
import com.example.socketmarket.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing():CompanyListing{
    return CompanyListing(
        name=name,
        symbol=symbol,
        exchange=exchange
    )
}

fun CompanyListing.CompanyListingEntity():CompanyListingEntity{
    return CompanyListingEntity(
        name=name,
        symbol=symbol,
        exchange=exchange
    )
}


fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}

