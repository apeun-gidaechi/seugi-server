package seugi.server.global.response

data class BaseResponse<T> (

    val code: Int,
    val success: Boolean,
    val message: String,
    val data: List<T>

)