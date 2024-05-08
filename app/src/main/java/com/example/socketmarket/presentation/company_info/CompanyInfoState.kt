package com.example.socketmarket.presentation.company_info

import com.example.socketmarket.data.remote.dto.IntradayInfo
import com.example.socketmarket.domain.model.CompanyInfo

data class CompanyInfoState(
    val stockInfos: List<IntradayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)