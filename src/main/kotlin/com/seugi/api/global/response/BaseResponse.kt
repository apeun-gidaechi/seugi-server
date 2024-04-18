package com.seugi.api.global.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BaseResponse<T> (

    val status: Int,
    val success: Boolean,
    val state: String? = "OK",
    val message: String,
    val data: T? = null

)
