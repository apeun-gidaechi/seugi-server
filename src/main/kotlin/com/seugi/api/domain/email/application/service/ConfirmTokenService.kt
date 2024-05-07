package com.seugi.api.domain.email.application.service

import org.springframework.stereotype.Service
import com.seugi.api.domain.email.application.exception.EmailErrorCode
import com.seugi.api.domain.email.port.`in`.ConfirmTokenUseCase
import com.seugi.api.domain.email.port.out.LoadEmailPort
import com.seugi.api.global.exception.CustomException

@Service
class ConfirmTokenService (
    private val loadEmailPort: LoadEmailPort
) : ConfirmTokenUseCase {

    override fun confirmToken(token: String, email: String) {
        val loadEmail = loadEmailPort.loadEmail(token)

        if (loadEmail != email) {
            throw CustomException(EmailErrorCode.EMAIL_NOT_MATCH)
        }
    }
}