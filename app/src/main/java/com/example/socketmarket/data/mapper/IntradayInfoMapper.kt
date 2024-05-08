package com.example.socketmarket.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.socketmarket.data.remote.dto.IntradayInfo
import com.example.socketmarket.data.remote.dto.IntradayInfoDto
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun IntradayInfoDto.toIntradayInfo(): IntradayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timestamp, formatter)
    return IntradayInfo(
        date = localDateTime,
        close = close
    )
}