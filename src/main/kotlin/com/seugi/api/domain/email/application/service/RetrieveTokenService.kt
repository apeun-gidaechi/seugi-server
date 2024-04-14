package com.seugi.api.domain.email.application.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import com.seugi.api.domain.email.application.exception.EmailErrorCode
import com.seugi.api.domain.email.port.`in`.RetrieveTokenUseCase
import com.seugi.api.domain.email.port.out.LoadTokenPort
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.response.BaseResponse

@Service
class RetrieveTokenService (
    private val loadTokenPort: LoadTokenPort
) : RetrieveTokenUseCase {

    override fun retrieveToken(code: String): BaseResponse<String> {
        val data = loadTokenPort.loadToken(code)
            .orElseThrow {
                CustomException(EmailErrorCode.CODE_NOT_EXIST)
            }

        return BaseResponse (
            success = true,
            status = HttpStatus.OK.value(),
            message = "토큰 발급 성공 !",
            data = data
        )
    }
}