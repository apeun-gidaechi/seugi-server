package seugi.server.domain.member.adapter.`in`.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import seugi.server.domain.member.adapter.`in`.dto.CreateMemberDTO
import seugi.server.domain.member.port.`in`.CreateMemberUseCase
import seugi.server.global.response.BaseResponse

@RestController
@RequestMapping("member")
class CreateMemberController (
    private val createMemberUseCase: CreateMemberUseCase
) {

    @PostMapping("create")
    fun createMember(@RequestBody memberDTO: CreateMemberDTO): BaseResponse<Any> {
        return createMemberUseCase.createMember(memberDTO)
    }

}