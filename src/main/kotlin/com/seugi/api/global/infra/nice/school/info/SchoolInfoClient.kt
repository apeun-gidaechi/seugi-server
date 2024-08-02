package com.seugi.api.global.infra.nice.school.info

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "schoolInfoClient", url = "\${nice.school.info.url}")
interface SchoolInfoClient {
    @GetMapping
    fun getSchoolInfo(
        @RequestParam("KEY") key: String,
        @RequestParam("Type") type: String,
        @RequestParam("pIndex") pIndex: Int,
        @RequestParam("pSize") pSize: Int,
        @RequestParam("SCHUL_NM") schoolNm: String,
    ): SchoolInfoResponse
}