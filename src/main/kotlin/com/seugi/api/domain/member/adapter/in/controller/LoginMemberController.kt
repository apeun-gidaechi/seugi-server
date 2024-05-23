package com.seugi.api.domain.member.adapter.`in`.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.seugi.api.domain.member.adapter.`in`.dto.req.LoginMemberRequest
import com.seugi.api.domain.member.application.port.`in`.LoginMemberUseCase
import com.seugi.api.global.auth.jwt.JwtInfo
import com.seugi.api.global.response.BaseResponse

@RestController
@RequestMapping("/member")
class LoginMemberController (
    private val loginMemberUseCase: LoginMemberUseCase
) {

    @PostMapping("/login")
    fun loginMember(@RequestBody memberDTO: LoginMemberRequest): BaseResponse<JwtInfo> {
        return loginMemberUseCase.loginMember(memberDTO)
    }

}