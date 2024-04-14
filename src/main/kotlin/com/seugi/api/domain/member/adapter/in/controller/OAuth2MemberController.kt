package com.seugi.api.domain.member.adapter.`in`.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.seugi.api.domain.member.adapter.`in`.dto.OAuth2MemberDTO
import com.seugi.api.domain.member.port.`in`.OAuth2MemberUseCase
import com.seugi.api.global.auth.jwt.JwtInfo
import com.seugi.api.global.response.BaseResponse

@RestController
@RequestMapping("/member")
class OAuth2MemberController (
    private val oAuth2MemberUseCase: OAuth2MemberUseCase
) {

    @PostMapping("/oauth2")
    fun socialLogin(code: String, provider: String): BaseResponse<JwtInfo> {
        return oAuth2MemberUseCase.process(code, provider)
    }

    @PostMapping("/oauth2/complete")
    fun setDetails(@RequestBody dto: OAuth2MemberDTO): BaseResponse<Unit> {
        return oAuth2MemberUseCase.complete(dto)
    }

}