package com.seugi.api.domain.email.adapter

import com.seugi.api.domain.email.adapter.out.entity.CodeEntity
import com.seugi.api.domain.email.adapter.out.entity.TokenEntity
import org.springframework.stereotype.Component
import com.seugi.api.domain.email.adapter.out.repository.CodeRepository
import com.seugi.api.domain.email.adapter.out.repository.TokenRepository
import com.seugi.api.domain.email.application.exception.EmailErrorCode
import com.seugi.api.domain.email.port.out.LoadEmailPort
import com.seugi.api.domain.email.port.out.LoadTokenPort
import com.seugi.api.domain.email.port.out.SaveEmailPort
import com.seugi.api.domain.email.port.out.SaveTokenPort
import com.seugi.api.global.exception.CustomException
import org.springframework.data.repository.findByIdOrNull
import java.util.Optional

@Component
class EmailAdapter (
    private val codeRepository: CodeRepository,
    private val tokenRepository: TokenRepository
) : SaveTokenPort, LoadTokenPort, SaveEmailPort, LoadEmailPort {

    override fun saveToken(code: String, token: String) {
        codeRepository.save(
            CodeEntity(code, token)
        )
    }

    override fun loadToken(code: String): String {
        val codeEntity: CodeEntity = codeRepository.findByIdOrNull(code)
            ?: throw CustomException(EmailErrorCode.CODE_NOT_EXIST)

        return codeEntity.token
    }

    override fun saveEmail(token: String, email: String) {
        tokenRepository.save(
            TokenEntity(token, email)
        )
    }

    override fun loadEmail(token: String): String {
        val tokenEntity: TokenEntity = tokenRepository.findByIdOrNull(token)
            ?: throw CustomException(EmailErrorCode.EMAIL_NOT_LOADED)

        return tokenEntity.email
    }

}