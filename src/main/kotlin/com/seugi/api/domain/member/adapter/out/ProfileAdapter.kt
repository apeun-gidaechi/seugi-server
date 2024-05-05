package com.seugi.api.domain.member.adapter.out

import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import com.seugi.api.domain.member.adapter.out.entity.ProfileEntity
import com.seugi.api.domain.member.adapter.out.repository.MemberRepository
import com.seugi.api.domain.member.adapter.out.repository.ProfileRepository
import com.seugi.api.domain.member.application.exception.MemberErrorCode
import com.seugi.api.domain.member.application.port.out.profile.LoadProfilePort
import com.seugi.api.global.exception.CustomException
import org.springframework.stereotype.Component

@Component
class MemberProfileAdapter (
    private val memberRepository: MemberRepository
    private val profileRepository: ProfileRepository
) : LoadProfilePort {

    override fun loadProfileWithMemberIdAndWorkspaceId(memberId: Long, workspaceId: String): ProfileEntity {
        val member: MemberEntity = memberRepository.findById(memberId)
            .orElseThrow {
                throw CustomException(MemberErrorCode.MEMBER_NOT_FOUND)
            }

        return profileRepository.findByMemberIdAndWorkspaceId(member, workspaceId)
    }

}