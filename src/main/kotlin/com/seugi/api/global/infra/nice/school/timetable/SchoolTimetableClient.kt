package com.seugi.api.global.infra.nice.school.timetable

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam


@FeignClient(name = "schoolMealClient", url = "\${nice.school.timetable.base.url}")
interface SchoolTimetableClient {
    @GetMapping("/hisTimetable")
    fun getHisTimetable(
        @RequestParam("KEY") key: String,
        @RequestParam("Type") type: String,
        @RequestParam("pIndex") pIndex: Int,
        @RequestParam("pSize") pSize: Int,
        @RequestParam("ATPT_OFCDC_SC_CODE") scCode: String,
        @RequestParam("SD_SCHUL_CODE") sdCode: String,
        @RequestParam("TI_FROM_YMD") startDate: String,
        @RequestParam("TI_TO_YMD") endDate: String,
    ): HisTimetable

    @GetMapping("/misTimetable")
    fun getMisTimetable(
        @RequestParam("KEY") key: String,
        @RequestParam("Type") type: String,
        @RequestParam("pIndex") pIndex: Int,
        @RequestParam("pSize") pSize: Int,
        @RequestParam("ATPT_OFCDC_SC_CODE") scCode: String,
        @RequestParam("SD_SCHUL_CODE") sdCode: String,
        @RequestParam("TI_FROM_YMD") startDate: String,
        @RequestParam("TI_TO_YMD") endDate: String,
    ): MisTimetable

    @GetMapping("/elsTimetable")
    fun getElsTimetable(
        @RequestParam("KEY") key: String,
        @RequestParam("Type") type: String,
        @RequestParam("pIndex") pIndex: Int,
        @RequestParam("pSize") pSize: Int,
        @RequestParam("ATPT_OFCDC_SC_CODE") scCode: String,
        @RequestParam("SD_SCHUL_CODE") sdCode: String,
        @RequestParam("TI_FROM_YMD") startDate: String,
        @RequestParam("TI_TO_YMD") endDate: String,
    ): ElsTimetable
}