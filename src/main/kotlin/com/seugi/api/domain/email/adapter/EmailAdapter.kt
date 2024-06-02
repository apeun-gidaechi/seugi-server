package com.seugi.api.domain.email.adapter

import com.seugi.api.domain.email.adapter.out.entity.CodeEntity
import org.springframework.stereotype.Component
import com.seugi.api.domain.email.adapter.out.repository.CodeRepository
import com.seugi.api.domain.email.application.exception.EmailErrorCode
import com.seugi.api.domain.email.port.out.LoadCodePort
import com.seugi.api.domain.email.port.out.SaveCodePort
import com.seugi.api.global.exception.CustomException
import org.springframework.data.repository.findByIdOrNull

@Component
class EmailAdapter (
    private val codeRepository: CodeRepository,
) : SaveCodePort, LoadCodePort {

    override fun saveCode(email: String, code: String) {
        codeRepository.save(
            CodeEntity(email, code)
        )
    }

    override fun loadCode(email: String): String {
        val codeEntity: CodeEntity = codeRepository.findByIdOrNull(email)
            ?: throw CustomException(EmailErrorCode.EMAIL_NOT_LOADED)

        return codeEntity.code
    }

}