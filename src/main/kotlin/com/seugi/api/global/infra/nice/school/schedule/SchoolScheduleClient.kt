package com.seugi.api.global.infra.nice.school.schedule

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam


@FeignClient(name = "scheduleClient", url = "\${nice.school.schedule.url}")
interface SchoolScheduleClient {
    @GetMapping
    fun getSchoolSchedule(
        @RequestParam("KEY") key: String,
        @RequestParam("Type") type: String,
        @RequestParam("pIndex") pIndex: Int,
        @RequestParam("pSize") pSize: Int,
        @RequestParam("ATPT_OFCDC_SC_CODE") scCode: String,
        @RequestParam("SD_SCHUL_CODE") sdCode: String,
        @RequestParam("AA_FROM_YMD") startDate: String,
        @RequestParam("AA_TO_YMD") endDate: String,
    ): SchoolScheduleResponse
}