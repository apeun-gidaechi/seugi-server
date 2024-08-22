package com.seugi.api.global.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.seugi.api.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BaseResponse<T>(

    val status: Int = HttpStatus.OK.value(),
    val success: Boolean = true,
    val state: String? = "OK",
    val message: String,
    val data: T? = null

) {

    // errorResponse constructor
    constructor(code: CustomErrorCode) : this(
        status = code.status.value(),
        success = false,
        state = code.state,
        message = code.message
    )

}

