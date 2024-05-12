package com.seugi.api.domain.member.application.service

import com.seugi.api.domain.member.adapter.`in`.dto.EditProfileDTO
import com.seugi.api.domain.member.adapter.out.MemberAdapter
import com.seugi.api.domain.member.application.model.value.MemberPicture
import com.seugi.api.domain.member.application.port.`in`.EditMemberUseCase
import com.seugi.api.domain.member.application.port.out.LoadProfilePort
import com.seugi.api.domain.member.application.port.out.SaveProfilePort
import com.seugi.api.global.response.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class EditMemberService (
    private val memberAdapter: MemberAdapter,
    private val loadProfilePort: LoadProfilePort,
    private val saveProfilePort: SaveProfilePort
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

    override fun editProfile(dto: EditProfileDTO, workspaceId: String, id: Long): BaseResponse<Unit> {
        val profile = loadProfilePort.loadProfileWithMemberIdAndWorkspaceId(id, workspaceId)

        profile.status = dto.status
        profile.nick = dto.nick
        profile.belong = dto.belong
        profile.location = dto.location
        profile.phone = dto.phone
        profile.spot = dto.spot
        profile.wire = dto.wire

        saveProfilePort.saveProfile(profile)

        return BaseResponse (
            status = HttpStatus.OK.value(),
            success = true,
            message = "프로필 수정 성공 !!",
        )
    }
}