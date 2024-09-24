package com.seugi.api.domain.member.application.service

import com.seugi.api.domain.member.application.port.`in`.LogoutMemberUseCase
import com.seugi.api.domain.member.application.port.out.LoadMemberPort
import com.seugi.api.domain.member.application.port.out.SaveMemberPort
import com.seugi.api.global.response.BaseResponse
import org.springframework.stereotype.Service

@Service
class LogoutMemberService(
    private val loadMemberPort: LoadMemberPort,
    private val saveMemberPort: SaveMemberPort,
) : LogoutMemberUseCase {

    override fun logoutMember(userId: Long, token: String): BaseResponse<Unit> {

        val member = loadMemberPort.loadMemberWithId(userId)

        member.removeFCMToken(token)

        saveMemberPort.saveMember(member)

        return BaseResponse(
            message = "로그아웃 성공!"
        )
    }

}