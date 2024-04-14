package com.seugi.api.domain.email.adapter.`in`

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import com.seugi.api.domain.email.port.`in`.RetrieveTokenUseCase
import com.seugi.api.global.response.BaseResponse

@RestController
@RequestMapping("/email")
class VerifyEmailController (
    private val retrieveTokenUseCase: RetrieveTokenUseCase
) {

    @GetMapping("/verify")
    fun getToken(
        @RequestParam code: String
    ): BaseResponse<String> {
        return retrieveTokenUseCase.retrieveToken(code)
    }

}