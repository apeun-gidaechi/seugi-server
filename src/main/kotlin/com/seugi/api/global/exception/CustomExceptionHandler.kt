package com.seugi.api.global.exception

import com.seugi.api.global.infra.discord.webhook.DiscordErrorSendService
import com.seugi.api.global.response.BaseResponse
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
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

    private fun printStackTrace(message: String?, e: Exception) {
        logger.error("Exception: $message", e)
    }

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(customException: CustomException): ResponseEntity<BaseResponse<Unit>> {
        return createErrorResponse(
            status = customException.customErrorCode.status,
            state = customException.customErrorCode.state,
            message = customException.customErrorCode.message
        )
    }

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFound(noResourceFoundException: NoResourceFoundException): ResponseEntity<BaseResponse<Unit>> {
        printStackTrace(noResourceFoundException.message, noResourceFoundException)
        return createErrorResponse(
            status = CommonErrorCode.NOT_FOUND.status,
            state = CommonErrorCode.NOT_FOUND.state,
            message = "잘못된 url로 요청하셨습니다 ${CommonErrorCode.NOT_FOUND.message}"
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleAllException(e: Exception, webRequest: WebRequest): ResponseEntity<BaseResponse<Unit>> {
        sendDiscordAlert(e, webRequest)
        printStackTrace(e.message, e)
        return createErrorResponse(
            status = CommonErrorCode.INTERNAL_SERVER_ERROR.status,
            state = CommonErrorCode.INTERNAL_SERVER_ERROR.state,
            message = "서버 오류가 발생하였습니다. ${e.message}",
        )
    }

    private fun createErrorResponse(
        status: HttpStatus,
        state: String,
        message: String,
    ): ResponseEntity<BaseResponse<Unit>> {

        val response = BaseResponse<Unit>(
            status = status.value(),
            state = state,
            success = false,
            message = message
        )

        return ResponseEntity(response, status)
    }

    private fun sendDiscordAlert(e: Exception, webRequest: WebRequest) {
        if (environment.activeProfiles.contains("prod")) {
            discordService.sendDiscordAlarm(e, webRequest)
        }
    }

}