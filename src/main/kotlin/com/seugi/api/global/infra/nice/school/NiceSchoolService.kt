package com.seugi.api.global.infra.nice.school

import com.seugi.api.domain.meal.domain.model.Meal
import com.seugi.api.domain.timetable.domain.model.Timetable
import com.seugi.api.domain.workspace.domain.model.SchoolInfo
import com.seugi.api.global.infra.nice.school.info.SchoolInfoClient
import com.seugi.api.global.infra.nice.school.info.SchoolInfoResponse
import com.seugi.api.global.infra.nice.school.meal.SchoolMealClient
import com.seugi.api.global.infra.nice.school.timetable.Row
import com.seugi.api.global.infra.nice.school.timetable.SchoolTimetableClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class NiceSchoolService(
    private val schoolInfoClient: SchoolInfoClient,
    private val schoolMealClient: SchoolMealClient,
    private val schoolTimetableClient: SchoolTimetableClient,
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
                menu = it.ddishNm,
                calorie = it.calInfo,
                mealInfo = it.ntrInfo,
                mealDate = it.mlsvYmd
            )
        } ?: emptyList()

        return meals
    }

    private fun niceTimetableToModel(niceData: Row): Timetable {
        return Timetable(
            grade = niceData.grade,
            classNum = niceData.classNm,
            time = niceData.perio,
            subject = niceData.itrtCntnt
        )
    }

    fun getSchoolTimeTable(schoolInfo: SchoolInfo, startData: String, endData: String): List<Timetable> {
        val timetables: MutableList<Timetable> = mutableListOf()
        when (schoolInfo.scType) {
            "고등학교" -> {
                schoolTimetableClient.getHisTimetable(
                    key = key,
                    type = "json",
                    pIndex = 1,
                    pSize = 100,
                    scCode = schoolInfo.scCode,
                    sdCode = schoolInfo.sdCode,
                    startDate = startData,
                    endDate = endData
                ).hisTimetable[1].row?.map {
                    timetables.add(
                        niceTimetableToModel(it)
                    )
                }
            }


            "중학교" -> {
                schoolTimetableClient.getMisTimetable(
                    key = key,
                    type = "json",
                    pIndex = 1,
                    pSize = 100,
                    scCode = schoolInfo.scCode,
                    sdCode = schoolInfo.sdCode,
                    startDate = startData,
                    endDate = endData
                ).misTimetable[1].row?.map {
                    timetables.add(
                        niceTimetableToModel(it)
                    )
                }
            }

            "초등학교" -> {
                schoolTimetableClient.getElsTimetable(
                    key = key,
                    type = "json",
                    pIndex = 1,
                    pSize = 100,
                    scCode = schoolInfo.scCode,
                    sdCode = schoolInfo.sdCode,
                    startDate = startData,
                    endDate = endData
                ).elsTimetable[1].row?.map {
                    timetables.add(
                        niceTimetableToModel(it)
                    )
                }
            }

            else -> {}
        }

        return timetables
    }


}