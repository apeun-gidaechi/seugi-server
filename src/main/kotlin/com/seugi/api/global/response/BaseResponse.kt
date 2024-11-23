package com.seugi.api.global.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.seugi.api.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BaseResponse<T>(

    override val status: Int = HttpStatus.OK.value(),
    override val success: Boolean = true,
    override val state: String = "OK",
    override val message: String,
    val data: T? = null,

    ) : ResponseInterface {

    // errorResponse constructor
    constructor(code: CustomErrorCode) : this(
        status = code.status.value(),
        success = false,
        state = code.state,
        message = code.message
    )

}

