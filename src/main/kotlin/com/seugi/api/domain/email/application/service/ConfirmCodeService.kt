package com.seugi.api.domain.email.application.service

import com.seugi.api.domain.email.application.exception.EmailErrorCode
import com.seugi.api.domain.email.port.`in`.ConfirmCodeUseCase
import com.seugi.api.domain.email.port.out.LoadCodePort
import com.seugi.api.global.exception.CustomException
import org.springframework.stereotype.Service

@Service
class ConfirmCodeService (
    private val loadCodePort: LoadCodePort
) : ConfirmCodeUseCase {

    override fun confirmCode(email: String, code: String) {
        val got = loadCodePort.loadCode(email)

        if (got != code) {
            throw CustomException(EmailErrorCode.CODE_NOT_MATCH)
        }
    }

}