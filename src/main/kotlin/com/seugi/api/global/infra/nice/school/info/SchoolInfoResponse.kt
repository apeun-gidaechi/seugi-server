package com.seugi.api.global.infra.nice.school.info

import com.fasterxml.jackson.annotation.JsonProperty

data class SchoolInfoResponse(
    @JsonProperty("schoolInfo") val schoolInfos: List<SchoolInfos>? = null,
    @JsonProperty("RESULT") val result: Result? = null,
)

data class SchoolInfos(
    @JsonProperty("head") val head: List<Head>? = null,
    @JsonProperty("row") val row: List<SchoolRow>? = null,
)

data class Head(
    @JsonProperty("list_total_count") val list_total_count: Int = 0,
    @JsonProperty("RESULT") val result: Result? = null,
)

data class Result(
    @JsonProperty("CODE") val code: String? = null,
    @JsonProperty("MESSAGE") val message: String? = null,
)

data class SchoolRow(
    @JsonProperty("ATPT_OFCDC_SC_CODE") val atptOfcdcScCode: String? = null,
    @JsonProperty("ATPT_OFCDC_SC_NM") val atptOfcdcScNm: String? = null,
    @JsonProperty("SD_SCHUL_CODE") val sdSchulCode: String? = null,
    @JsonProperty("SCHUL_NM") val schulNm: String? = null,
    @JsonProperty("ENG_SCHUL_NM") val engSchulNm: String? = null,
    @JsonProperty("SCHUL_KND_SC_NM") val schulKndScNm: String? = null,
    @JsonProperty("LCTN_SC_NM") val lctnScNm: String? = null,
    @JsonProperty("JU_ORG_NM") val juOrgNm: String? = null,
    @JsonProperty("FOND_SC_NM") val fondScNm: String? = null,
    @JsonProperty("ORG_RDNZC") val orgRdnzc: String? = null,
    @JsonProperty("ORG_RDNMA") val orgRdnma: String? = null,
    @JsonProperty("ORG_RDNDA") val orgRdnda: String? = null,
    @JsonProperty("ORG_TELNO") val orgTelno: String? = null,
    @JsonProperty("HMPG_ADRES") val hmpgAdres: String? = null,
    @JsonProperty("COEDU_SC_NM") val coeduScNm: String? = null,
    @JsonProperty("ORG_FAXNO") val orgFaxno: String? = null,
    @JsonProperty("HS_SC_NM") val hsScNm: String? = null,
    @JsonProperty("INDST_SPECL_CCCCL_EXST_YN") val indstSpeclCccclExstYn: String? = null,
    @JsonProperty("HS_GNRL_BUSNS_SC_NM") val hsGnrlBusnsScNm: String? = null,
    @JsonProperty("SPCLY_PURPS_HS_ORD_NM") val spclyPurpsHsOrdNm: String? = null,
    @JsonProperty("ENE_BFE_SEHF_SC_NM") val eneBfeSehfScNm: String? = null,
    @JsonProperty("DGHT_SC_NM") val dghtScNm: String? = null,
    @JsonProperty("FOND_YMD") val fondYmd: String? = null,
    @JsonProperty("FOAS_MEMRD") val foasMemrd: String? = null,
    @JsonProperty("LOAD_DTM") val loadDtm: String? = null,
)