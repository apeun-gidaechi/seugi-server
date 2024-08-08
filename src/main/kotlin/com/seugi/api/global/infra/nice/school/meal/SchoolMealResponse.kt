package com.seugi.api.global.infra.nice.school.meal

import com.fasterxml.jackson.annotation.JsonProperty
import com.seugi.api.global.infra.nice.school.info.Result

data class SchoolMealResponse(
    @JsonProperty("mealServiceDietInfo") val mealServiceDietInfos: List<MealInfos>? = null,
    @JsonProperty("RESULT") val result: Result? = null,
)

data class MealInfos(
    @JsonProperty("head") val head: List<Head>? = null,
    @JsonProperty("row") val row: List<Row>? = null,
)

data class Head(
    @JsonProperty("list_total_count") val list_total_count: Int = 0,
    @JsonProperty("RESULT") val result: Result? = null,
)

data class Result(
    @JsonProperty("CODE") val code: String? = null,
    @JsonProperty("MESSAGE") val message: String? = null,
)

data class Row(
    @JsonProperty("ATPT_OFCDC_SC_CODE") val atptOfcdcScCode: String? = null,
    @JsonProperty("ATPT_OFCDC_SC_NM") val atptOfcdcScNm: String? = null,
    @JsonProperty("SD_SCHUL_CODE") val sdSchulCode: String? = null,
    @JsonProperty("SCHUL_NM") val schulNm: String? = null,
    @JsonProperty("MMEAL_SC_CODE") val mmealScCode: String? = null,
    @JsonProperty("MMEAL_SC_NM") val mmealScNm: String? = null,
    @JsonProperty("MLSV_YMD") val mlsvYmd: String? = null,
    @JsonProperty("MLSV_FGR") val mlsvFgr: Float? = null,
    @JsonProperty("DDISH_NM") val ddishNm: String? = null,
    @JsonProperty("ORPLC_INFO") val orplcInfo: String? = null,
    @JsonProperty("CAL_INFO") val calInfo: String? = null,
    @JsonProperty("NTR_INFO") val ntrInfo: String? = null,
    @JsonProperty("MLSV_FROM_YMD") val mlsvFromYmd: String? = null,
    @JsonProperty("MLSV_TO_YMD") val mlsvToYmd: String? = null,
    @JsonProperty("LOAD_DTM") val loadDtm: String? = null,
)