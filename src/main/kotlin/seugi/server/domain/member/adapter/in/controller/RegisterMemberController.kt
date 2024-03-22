package seugi.server.domain.member.adapter.`in`.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import seugi.server.domain.member.adapter.`in`.dto.RegisterMemberDTO
import seugi.server.domain.member.port.`in`.RegisterMemberUseCase
import seugi.server.global.response.BaseResponse

@RestController
@RequestMapping("member")
class RegisterMemberController (
    private val registerMemberUseCase: RegisterMemberUseCase
) {

    @PostMapping("register")
    fun createMember(@RequestBody memberDTO: RegisterMemberDTO): BaseResponse<Any> {
        return registerMemberUseCase.registerMember(memberDTO)
    }

}