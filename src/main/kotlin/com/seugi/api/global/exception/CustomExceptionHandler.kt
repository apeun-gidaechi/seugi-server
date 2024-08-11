package com.seugi.api.global.exception

import com.seugi.api.global.infra.discord.webhook.DiscordErrorSendService
import com.seugi.api.global.response.BaseResponse
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class CustomExceptionHandler(
    private val discordService: DiscordErrorSendService,
    private val environment: Environment,
) {

    private val logger = LoggerFactory.getLogger(CustomExceptionHandler::class.java)

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(customException: CustomException): ResponseEntity<BaseResponse<Unit>> {
        return createErrorResponse(
            status = customException.customErrorCode.status.value(),
            state = customException.customErrorCode.state,
            message = customException.customErrorCode.message,
            exception = customException
        )
    }

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFound(noResourceFoundException: NoResourceFoundException): ResponseEntity<BaseResponse<Unit>> {
        return createErrorResponse(
            status = CommonErrorCode.NOT_FOUND.status.value(),
            state = CommonErrorCode.NOT_FOUND.state,
            message = "잘못된 url로 요청하셨습니다 ${CommonErrorCode.NOT_FOUND.message}",
            exception = noResourceFoundException
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleAllException(e: Exception, webRequest: WebRequest): ResponseEntity<BaseResponse<Unit>> {
        e.printStackTrace()
        sendDiscordAlert(e, webRequest)

        return createErrorResponse(
            status = CommonErrorCode.INTERNAL_SERVER_ERROR.status.value(),
            state = CommonErrorCode.INTERNAL_SERVER_ERROR.state,
            message = "서버 오류가 발생하였습니다. ${e.message}",
            exception = e
        )
    }

    private fun createErrorResponse(
        status: Int,
        state: String,
        message: String,
        exception: Exception,
    ): ResponseEntity<BaseResponse<Unit>> {
        logger.error("Exception: $message", exception)

        val response = BaseResponse<Unit>(
            status = status,
            state = state,
            success = false,
            message = message
        )

        return ResponseEntity(response, CommonErrorCode.INTERNAL_SERVER_ERROR.status)
    }

    private fun sendDiscordAlert(e: Exception, webRequest: WebRequest) {
        if (environment.activeProfiles.contains("prod")) {
            discordService.sendDiscordAlarm(e, webRequest)
        }
    }

}