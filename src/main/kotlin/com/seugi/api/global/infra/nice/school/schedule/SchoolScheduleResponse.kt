package com.seugi.api.global.infra.nice.school.schedule

import com.fasterxml.jackson.annotation.JsonProperty

data class SchoolScheduleResponse(
    @JsonProperty("SchoolSchedule") val schoolSchedule: List<ScheduleInfo>? = null,
)

data class ScheduleInfo(
    @JsonProperty("head") val head: List<Head>? = null,
    @JsonProperty("row") val row: List<ScheduleRow>? = null,
)

data class Head(
    @JsonProperty("list_total_count") val listTotalCount: Int = 0,
    @JsonProperty("RESULT") val result: Result? = null,
)

data class Result(
    @JsonProperty("CODE") val code: String? = null,
    @JsonProperty("MESSAGE") val message: String? = null,
)

data class ScheduleRow(
    @JsonProperty("ATPT_OFCDC_SC_CODE") val atptOfcdcScCode: String? = null,
    @JsonProperty("SD_SCHUL_CODE") val sdSchulCode: String? = null,
    @JsonProperty("AY") val ay: String? = null,
    @JsonProperty("AA_YMD") val aaYmd: String? = null,
    @JsonProperty("ATPT_OFCDC_SC_NM") val atptOfcdcScNm: String? = null,
    @JsonProperty("SCHUL_NM") val schulNm: String? = null,
    @JsonProperty("DGHT_CRSE_SC_NM") val dghtCrseScNm: String? = null,
    @JsonProperty("SCHUL_CRSE_SC_NM") val schulCrseScNm: String? = null,
    @JsonProperty("EVENT_NM") val eventNm: String? = null,
    @JsonProperty("EVENT_CNTNT") val eventCntnt: String? = null,
    @JsonProperty("ONE_GRADE_EVENT_YN") val oneGradeEventYn: String? = null,
    @JsonProperty("TW_GRADE_EVENT_YN") val twGradeEventYn: String? = null,
    @JsonProperty("THREE_GRADE_EVENT_YN") val threeGradeEventYn: String? = null,
    @JsonProperty("FR_GRADE_EVENT_YN") val frGradeEventYn: String? = null,
    @JsonProperty("FIV_GRADE_EVENT_YN") val fivGradeEventYn: String? = null,
    @JsonProperty("SIX_GRADE_EVENT_YN") val sixGradeEventYn: String? = null,
    @JsonProperty("SBTR_DD_SC_NM") val sbtrDdScNm: String? = null,
    @JsonProperty("LOAD_DTM") val loadDtm: String? = null,
)