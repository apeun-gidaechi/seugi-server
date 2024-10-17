package com.seugi.api.global.infra.nice.school

import com.seugi.api.domain.meal.domain.model.Meal
import com.seugi.api.domain.workspace.domain.model.SchoolInfo
import com.seugi.api.global.infra.nice.school.info.SchoolInfoClient
import com.seugi.api.global.infra.nice.school.info.SchoolInfoResponse
import com.seugi.api.global.infra.nice.school.meal.SchoolMealClient
import com.seugi.api.global.infra.nice.school.meal.SchoolMealConvertor
import com.seugi.api.global.infra.nice.school.schedule.ScheduleRow
import com.seugi.api.global.infra.nice.school.schedule.SchoolScheduleClient
import com.seugi.api.global.infra.nice.school.timetable.Row
import com.seugi.api.global.infra.nice.school.timetable.SchoolTimetableClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class NiceSchoolService(
    private val schoolInfoClient: SchoolInfoClient,
    private val schoolMealClient: SchoolMealClient,
    private val schoolTimetableClient: SchoolTimetableClient,
    private val schoolScheduleClient: SchoolScheduleClient,
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
                scCode = "x",
                sdCode = "x",
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

    fun getSchoolMeal(schoolInfo: SchoolInfo, startData: String, endData: String): List<Meal> {
        val mealResponse = schoolMealClient.getSchoolMeal(
            key = key,
            type = "json",
            pIndex = 1,
            pSize = 100,
            scCode = schoolInfo.scCode,
            sdCode = schoolInfo.sdCode,
            startDate = startData,
            endDate = endData
        )

        val row = mealResponse.mealServiceDietInfos?.get(1)?.row

        val meals = row?.map {
            Meal(
                mealType = it.mmealScNm,
                menu = SchoolMealConvertor.removeNumbersAndDots(it.ddishNm),
                calorie = it.calInfo,
                mealInfo = it.ntrInfo,
                mealDate = SchoolDateConvertor.dateFormat(it.mlsvYmd)
            )
        } ?: emptyList()

        return meals
    }

    fun getSchoolTimeTable(
        schoolInfo: SchoolInfo,
        startDate: String,
        endDate: String,
        workspaceId: String,
    ): List<Row>? {
        return when (schoolInfo.scType) {
            "고등학교" -> {
                schoolTimetableClient.getHisTimetable(
                    key = key,
                    type = "json",
                    pIndex = 1,
                    pSize = 1000,
                    scCode = schoolInfo.scCode,
                    sdCode = schoolInfo.sdCode,
                    startDate = startDate.replace("-", ""),
                    endDate = endDate.replace("-", "")
                ).hisTimetable?.get(1)?.row
            }


            "중학교" -> {
                schoolTimetableClient.getMisTimetable(
                    key = key,
                    type = "json",
                    pIndex = 1,
                    pSize = 1000,
                    scCode = schoolInfo.scCode,
                    sdCode = schoolInfo.sdCode,
                    startDate = startDate,
                    endDate = endDate
                ).misTimetable?.get(1)?.row
            }

            "초등학교" -> {
                schoolTimetableClient.getElsTimetable(
                    key = key,
                    type = "json",
                    pIndex = 1,
                    pSize = 1000,
                    scCode = schoolInfo.scCode,
                    sdCode = schoolInfo.sdCode,
                    startDate = startDate,
                    endDate = endDate
                ).elsTimetable?.get(1)?.row
            }

            else -> emptyList()
        }
    }

    fun getSchoolSchedule(
        schoolInfo: SchoolInfo,
    ): List<ScheduleRow> {
        return schoolScheduleClient.getSchoolSchedule(
            key = key,
            type = "json",
            pIndex = 1,
            pSize = 1000,
            scCode = schoolInfo.scCode,
            sdCode = schoolInfo.sdCode,
            startDate = LocalDate.now().year.toString() + "01" + "01",
            endDate = LocalDate.now().year.toString() + "12" + "31"
        ).schoolSchedule?.get(1)?.row ?: emptyList()
    }

}