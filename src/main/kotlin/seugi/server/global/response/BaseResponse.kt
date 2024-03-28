package seugi.server.global.response

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BaseResponse<T> (

    val status: HttpStatus,
    val success: Boolean,
    val state: String? = "NONE",
    val message: String,
    val data: T? = null

)

