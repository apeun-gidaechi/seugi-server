package com.seugi.api.global.infra.nice.school.timetable

import com.fasterxml.jackson.annotation.JsonProperty

data class HisTimetable(
    @JsonProperty("hisTimetable") val hisTimetable: List<Item>?,
    @JsonProperty("RESULT") val result: Result?,
)

data class MisTimetable(
    @JsonProperty("misTimetable") val misTimetable: List<Item>?,
    @JsonProperty("RESULT") val result: Result?,
)

data class ElsTimetable(
    @JsonProperty("elsTimetable") val elsTimetable: List<Item>?,
    @JsonProperty("RESULT") val result: Result?,
)

data class Item(
    @JsonProperty("head") val head: List<Head>? = null,
    @JsonProperty("row") val row: List<Row>? = null,
)

data class Head(
    @JsonProperty("list_total_count") val listTotalCount: Int? = null,
    @JsonProperty("RESULT") val result: Result? = null,
)

data class Result(
    @JsonProperty("CODE") val code: String,
    @JsonProperty("MESSAGE") val message: String,
)

data class Row(
    @JsonProperty("ATPT_OFCDC_SC_CODE") val atptOfcdcScCode: String?,
    @JsonProperty("ATPT_OFCDC_SC_NM") val atptOfcdcScNm: String?,
    @JsonProperty("SD_SCHUL_CODE") val sdSchulCode: String?,
    @JsonProperty("SCHUL_NM") val schulNm: String?,
    @JsonProperty("AY") val ay: String?,
    @JsonProperty("SEM") val sem: String?,
    @JsonProperty("ALL_TI_YMD") val allTiYmd: String?,
    @JsonProperty("DGHT_CRSE_SC_NM") val dghtCrseScNm: String?,
    @JsonProperty("ORD_SC_NM") val ordScNm: String?,
    @JsonProperty("DDDEP_NM") val dddepNm: String?,
    @JsonProperty("GRADE") val grade: String?,
    @JsonProperty("CLRM_NM") val clrmNm: String?,
    @JsonProperty("CLASS_NM") val classNm: String?,
    @JsonProperty("PERIO") val perio: String?,
    @JsonProperty("ITRT_CNTNT") val itrtCntnt: String?,
    @JsonProperty("LOAD_DTM") val loadDtm: String?,
)