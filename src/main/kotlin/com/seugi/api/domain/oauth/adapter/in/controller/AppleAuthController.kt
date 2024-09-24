package com.seugi.api.domain.oauth.adapter.`in`.controller

import com.seugi.api.domain.oauth.adapter.`in`.dto.request.AppleCodeRequest
import com.seugi.api.domain.oauth.port.`in`.AppleAuthUseCase
import com.seugi.api.global.auth.jwt.JwtInfo
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/oauth/apple")
class AppleAuthController (
    private val service: AppleAuthUseCase
) {

    @PostMapping("/authenticate")
    fun authentication(@RequestBody req: AppleCodeRequest): BaseResponse<JwtInfo> {
        return service.authenticate(req)
    }

}