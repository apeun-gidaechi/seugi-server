package com.seugi.api.global.infra.nice.school.meal

object SchoolMealConvertor {
    fun removeNumbersAndDots(input: String?): String {
        val regex = "\\(\\d+(\\.\\d+)*\\)".toRegex()
        val cleanedString = input
            ?.replace(regex, "") // 숫자와 마침표 조합을 제거
            ?.replace("\\s+".toRegex(), "") // 다중 공백을 단일 공백으로 변환
            ?.trim()
        return cleanedString ?: ""
    }

}