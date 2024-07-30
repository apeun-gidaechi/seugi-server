package com.seugi.api.global.exception

import com.seugi.api.global.infra.discord.webhook.DiscordErrorSendService
import com.seugi.api.global.response.BaseResponse
import org.springframework.core.env.Environment
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest


@RestControllerAdvice
class CustomExceptionHandler(
    private val discordService: DiscordErrorSendService,
    private val environment: Environment,
) {

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(customException: CustomException): ResponseEntity<Any> {
        val response = BaseResponse<Unit>(
            status = customException.customErrorCode.status.value(),
            state = customException.customErrorCode.state,
            success = false,
            message = customException.customErrorCode.message,
        )

        return ResponseEntity(response, customException.customErrorCode.status)
    }

    @ExceptionHandler
    fun handleException(e: Exception, webRequest: WebRequest) {
        e.printStackTrace()
        sendDiscordAlert(e, webRequest)
    }

    fun sendDiscordAlert(e: Exception, webRequest: WebRequest) {
        if (environment.activeProfiles.contains("prod")) {
            discordService.sendDiscordAlarm(e, webRequest)
        }
    }

}