package com.seugi.api.domain.profile.adapter.out

import com.seugi.api.domain.member.adapter.out.repository.MemberRepository
import com.seugi.api.domain.profile.adapter.out.repository.ProfileRepository
import com.seugi.api.domain.member.application.exception.MemberErrorCode
import com.seugi.api.domain.profile.adapter.out.mapper.ProfileMapper
import com.seugi.api.domain.profile.application.exception.ProfileErrorCode
import com.seugi.api.domain.profile.application.model.Profile
import com.seugi.api.domain.profile.application.port.out.LoadProfilePort
import com.seugi.api.domain.profile.application.port.out.SaveProfilePort
import com.seugi.api.global.exception.CustomException
import org.springframework.stereotype.Component

@Component
class ProfileAdapter(
    private val memberRepository: MemberRepository,
    private val profileRepository: ProfileRepository,
    private val profileMapper: ProfileMapper
) : LoadProfilePort, SaveProfilePort {

    override fun loadProfile(memberId: Long, workspaceId: String): Profile {
        val memberEntity = memberRepository.findById(memberId)
            .orElseThrow {
                CustomException(MemberErrorCode.MEMBER_NOT_FOUND)
            }

        val profileEntity = profileRepository.findByMemberIdAndWorkspaceId(memberEntity, workspaceId)
            .orElseThrow {
                CustomException(ProfileErrorCode.PROFILE_NOT_FOUND)
            }

        return profileMapper.toDomain(profileEntity)

    }

    override fun saveProfile(profile: Profile) {
        profileRepository.save(
            profileMapper.toEntity(profile)
        )
    }

}