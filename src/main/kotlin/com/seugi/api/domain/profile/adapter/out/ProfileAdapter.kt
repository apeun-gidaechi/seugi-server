package com.seugi.api.domain.profile.adapter.out

import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import com.seugi.api.domain.profile.adapter.out.entity.ProfileEntity
import com.seugi.api.domain.member.adapter.out.repository.MemberRepository
import com.seugi.api.domain.profile.adapter.out.repository.ProfileRepository
import com.seugi.api.domain.member.application.exception.MemberErrorCode
import com.seugi.api.domain.profile.application.exception.ProfileErrorCode
import com.seugi.api.domain.profile.application.port.out.LoadProfilePort
import com.seugi.api.domain.profile.application.port.out.SaveProfilePort
import com.seugi.api.global.exception.CustomException
import org.springframework.stereotype.Component

@Component
class ProfileAdapter (
    private val memberRepository: MemberRepository,
    private val profileRepository: ProfileRepository
) : LoadProfilePort, SaveProfilePort {

    override fun loadProfile(memberId: Long, workspaceId: String): ProfileEntity {
        val member: MemberEntity = memberRepository.findById(memberId)
            .orElseThrow {
                throw CustomException(MemberErrorCode.MEMBER_NOT_FOUND)
            }

        return profileRepository.findByMemberIdAndWorkspaceId(member, workspaceId)
            .orElseThrow {
                throw CustomException(ProfileErrorCode.PROFILE_NOT_FOUND)
            }
    }

    override fun saveProfile(profile: ProfileEntity) {
        profileRepository.save(profile)
    }

}