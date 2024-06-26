package com.seugi.api.domain.member.application.service

import com.seugi.api.domain.member.application.model.value.MemberDeleted
import com.seugi.api.domain.member.application.port.`in`.RemoveMemberUseCase
import com.seugi.api.domain.member.application.port.out.LoadMemberPort
import com.seugi.api.domain.member.application.port.out.SaveMemberPort
import com.seugi.api.global.response.BaseResponse
import org.springframework.stereotype.Service

@Service
class RemoveMemberService(
    private val loadMemberPort: LoadMemberPort,
    private val saveMemberPort: SaveMemberPort
) : RemoveMemberUseCase {

    override fun removeMember(id: Long): BaseResponse<Unit> {
        val member = loadMemberPort.loadMemberWithId(id)

        member.deleted = MemberDeleted(true)

        saveMemberPort.saveMember(member)

        return BaseResponse(
            message = "멤버가 삭제되었어요."
        )
    }

}