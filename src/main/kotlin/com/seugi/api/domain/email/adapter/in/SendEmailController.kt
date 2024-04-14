package com.seugi.api.domain.email.adapter.`in`

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import com.seugi.api.domain.email.port.`in`.CreateTokenUseCase
import com.seugi.api.global.response.BaseResponse

@RestController
@RequestMapping("/email")
class SendEmailController (
    private val createTokenUseCase: CreateTokenUseCase
) {

    @GetMapping("/send")
    fun sendEmail (
        @RequestParam email: String
    ): BaseResponse<Unit> {
        return createTokenUseCase.createToken(email)
    }

}