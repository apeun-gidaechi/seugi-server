package com.seugi.api.global.infra.nice.school.meal

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "schoolMealClient", url = "\${nice.school.meal.url}")
interface SchoolMealClient {
    @GetMapping
    fun getSchoolMeal(
        @RequestParam("KEY") key: String,
        @RequestParam("Type") type: String,
        @RequestParam("pIndex") pIndex: Int,
        @RequestParam("pSize") pSize: Int,
        @RequestParam("ATPT_OFCDC_SC_CODE") scCode: String,
        @RequestParam("SD_SCHUL_CODE") sdCode: String,
        @RequestParam("MLSV_FROM_YMD") startDate: String,
        @RequestParam("MLSV_TO_YMD") endDate: String,
    ): SchoolMealResponse
}