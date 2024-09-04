package com.seugi.api.domain.oauth.adapter.`in`.controller

import com.seugi.api.domain.oauth.adapter.`in`.dto.GoogleAuthRequest
import com.seugi.api.domain.oauth.port.`in`.GoogleAuthUseCase
import com.seugi.api.global.auth.jwt.JwtInfo
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/oauth/google")
class GoogleAuthController (
    private val service: GoogleAuthUseCase
) {

    @PostMapping("/authenticate")
    fun authentication(@RequestBody req: GoogleAuthRequest): BaseResponse<JwtInfo> {
        return service.authenticate(req)
    }

}