package com.seugi.api.domain.email.application.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import com.seugi.api.domain.email.port.`in`.CreateTokenUseCase
import com.seugi.api.domain.email.port.`in`.SendEmailUseCase
import com.seugi.api.domain.email.port.out.SaveEmailPort
import com.seugi.api.domain.email.port.out.SaveTokenPort
import com.seugi.api.global.response.BaseResponse
import java.util.Random

@Service
class CreateTokenService (
    private val saveTokenPort: SaveTokenPort,
    private val sendEmailUseCase: SendEmailUseCase,
    private val saveEmailPort: SaveEmailPort
) : CreateTokenUseCase {

    val alphaString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    val alphaNumber = "0123456789"

    val random = Random()

    override fun createToken(email: String): BaseResponse<Unit> {
        val tokenBuffer = StringBuffer()
        val codeBuffer = StringBuffer()

        for (i in 0 until 6)
            codeBuffer.append(
                alphaNumber[random.nextInt(alphaNumber.length)]
            )

        for (i in alphaString.indices)
            tokenBuffer.append(
                alphaString[random.nextInt(alphaString.length)]
            )

        saveTokenPort.saveToken(codeBuffer.toString(), tokenBuffer.toString())
        saveEmailPort.saveEmail(tokenBuffer.toString(), email)

        sendEmailUseCase.sendEmail(
            email,
            "테스트용입니다",
            codeBuffer.toString()
        )

        return BaseResponse (
            status = HttpStatus.OK.value(),
            success = true,
            message = "전송 성공 !",
        )
    }
}