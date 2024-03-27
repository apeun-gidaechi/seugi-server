package seugi.server.global.exception

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import seugi.server.global.response.BaseResponse

@RestControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(customException: CustomException): BaseResponse<String> {
        return BaseResponse (
            code = customException.customErrorCode.code,
            success = false,
            message = customException.customErrorCode.msg,
            data = emptyList()
        )
    }

}