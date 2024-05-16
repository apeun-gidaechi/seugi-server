package com.seugi.api.domain.member.application.service

import com.seugi.api.domain.member.adapter.out.MemberAdapter
import com.seugi.api.domain.member.application.model.value.MemberPicture
import com.seugi.api.domain.member.application.port.`in`.EditMemberUseCase
import com.seugi.api.global.response.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class EditMemberService (
    private val memberAdapter: MemberAdapter,
) : EditMemberUseCase {

    override fun editPicture(url: String, id: Long): BaseResponse<Unit> {
        val member = memberAdapter.loadMemberWithId(id)

        member.picture = MemberPicture(url)

        memberAdapter.saveMember(member)

        return BaseResponse (
            status = HttpStatus.OK.value(),
            success = true,
            message = "프로필 사진 변경 성공 !!"
        )
    }

}