package com.seugi.api.domain.email.application.service

import com.seugi.api.domain.email.port.`in`.CreateCodeUseCase
import com.seugi.api.domain.email.port.`in`.SendEmailUseCase
import com.seugi.api.domain.email.port.out.SaveCodePort
import com.seugi.api.global.response.BaseResponse
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class CreateCodeService (
    private val sendEmailUseCase: SendEmailUseCase,
    private val saveCodePort: SaveCodePort
) : CreateCodeUseCase {

    override fun createToken(email: String): BaseResponse<Unit> {
        val random = Random.nextInt(100000, 1000000).toString()

        saveCodePort.saveCode(email, random)

        sendEmailUseCase.sendEmail(
            email,
            "테스트용입니다",
            random
        )

        return BaseResponse (
            message = "전송 성공 !",
        )
    }

}