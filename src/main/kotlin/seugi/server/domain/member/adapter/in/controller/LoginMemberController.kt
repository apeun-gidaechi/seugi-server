package seugi.server.domain.member.adapter.`in`.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import seugi.server.domain.member.adapter.`in`.dto.LoginMemberDTO
import seugi.server.domain.member.port.`in`.LoginMemberUseCase
import seugi.server.global.auth.jwt.JwtInfo
import seugi.server.global.response.BaseResponse

@RestController
@RequestMapping("/member")
class LoginMemberController (
    private val loginMemberUseCase: LoginMemberUseCase
) {

    @PostMapping("/login")
    fun loginMember(@RequestBody memberDTO: LoginMemberDTO): BaseResponse<JwtInfo> {
        return loginMemberUseCase.loginMember(memberDTO)
    }

}