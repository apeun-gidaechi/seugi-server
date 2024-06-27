package com.seugi.api.domain.member.application.service

import com.seugi.api.domain.member.adapter.`in`.dto.res.RetrieveMemberResponse
import com.seugi.api.domain.member.application.model.Member
import com.seugi.api.domain.member.application.port.`in`.RetrieveMemberUseCase
import com.seugi.api.domain.member.application.port.out.LoadMemberPort
import com.seugi.api.global.response.BaseResponse
import org.springframework.stereotype.Service

@Service
class RetrieveMemberService (
    private val loadMemberPort: LoadMemberPort
) : RetrieveMemberUseCase {

    override fun retrieveMember(memberId: Long): BaseResponse<RetrieveMemberResponse> {
        val member: Member = loadMemberPort.loadMemberWithId(memberId)

        return BaseResponse (
            message = "멤버 정보 조회 성공 !!",
            data = RetrieveMemberResponse(member)
        )
    }
}