package com.seugi.api.domain.member.adapter.`in`.controller

import com.seugi.api.domain.member.adapter.`in`.dto.LoginMemberDTO
import com.seugi.api.domain.member.port.`in`.LoginMemberUseCase
import com.seugi.api.global.auth.jwt.JwtInfo
import com.seugi.api.global.response.BaseResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class TestMemberController (
    @Value("\${test.email}") val email: String,
    @Value("\${test.password}") val password: String,

    private val loginMemberUseCase: LoginMemberUseCase
) {

    @PostMapping("/test")
    fun testLogin(): BaseResponse<JwtInfo> {
        return loginMemberUseCase.loginMember(
            LoginMemberDTO(
                email,
                password
            )
        )
    }

}