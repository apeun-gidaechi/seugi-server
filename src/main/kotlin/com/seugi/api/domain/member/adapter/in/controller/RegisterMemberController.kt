package com.seugi.api.domain.member.adapter.`in`.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.seugi.api.domain.member.adapter.`in`.dto.req.RegisterMemberRequest
import com.seugi.api.domain.member.application.port.`in`.RegisterMemberUseCase
import com.seugi.api.global.response.BaseResponse

@RestController
@RequestMapping("/member")
class RegisterMemberController (
    private val registerMemberUseCase: RegisterMemberUseCase
) {

    @PostMapping("/register")
    fun createMember(@RequestBody memberDTO: RegisterMemberRequest): BaseResponse<String> {
        return registerMemberUseCase.registerMember(memberDTO)
    }

}