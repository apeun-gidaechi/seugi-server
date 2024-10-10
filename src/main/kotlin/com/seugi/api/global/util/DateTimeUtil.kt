package com.seugi.api.global.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeUtil {
    val localDateTime: LocalDateTime =
        LocalDateTime.parse("0001-01-01T00:00:00.000000", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))
}