package com.seugi.api.global.infra.nice.school

object SchoolDateConvertor {
    fun dateFormat(date: String?): String {
        return date?.substring(0, 4) + "-" + date?.substring(4, 6) + "-" + date?.substring(6, 8)
    }
}