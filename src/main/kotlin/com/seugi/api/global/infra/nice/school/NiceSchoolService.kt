package com.seugi.api.global.infra.nice.school

import com.seugi.api.domain.workspace.domain.model.SchoolInfo
import com.seugi.api.global.infra.nice.school.info.SchoolInfoClient
import com.seugi.api.global.infra.nice.school.info.SchoolInfoResponse
import com.seugi.api.global.infra.nice.school.meal.SchoolMealClient
import com.seugi.api.global.infra.nice.school.meal.SchoolMealResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class NiceSchoolService(
    private val schoolInfoClient: SchoolInfoClient,
    @Value("\${nice.key}") private val key: String,
) {

    fun getSchoolInfo(schoolName: String): SchoolInfo {
        val schoolResponse: SchoolInfoResponse = schoolInfoClient.getSchoolInfo(
            key = key,
            type = "json",
            pIndex = 1,
            pSize = 5,
            schoolNm = schoolName
        )

        val schoolData = schoolResponse.schoolInfos?.get(1)?.row?.get(0)

        return if (schoolData == null) {
            SchoolInfo(
                scCode = " ",
                sdCode = " ",
                scType = "기타"
            )
        } else {
            SchoolInfo(
                scCode = schoolData.atptOfcdcScCode!!,
                sdCode = schoolData.sdSchulCode!!,
                scType = schoolData.schulKndScNm!!
            )
        }


    }


}