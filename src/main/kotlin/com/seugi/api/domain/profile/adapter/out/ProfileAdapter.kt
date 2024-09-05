package com.seugi.api.domain.profile.adapter.out

import com.seugi.api.domain.member.adapter.out.repository.MemberRepository
import com.seugi.api.domain.profile.adapter.out.repository.ProfileRepository
import com.seugi.api.domain.member.application.exception.MemberErrorCode
import com.seugi.api.domain.profile.adapter.out.mapper.ProfileMapper
import com.seugi.api.domain.profile.application.exception.ProfileErrorCode
import com.seugi.api.domain.profile.application.model.Profile
import com.seugi.api.domain.profile.application.port.out.ExistProfilePort
import com.seugi.api.domain.profile.application.port.out.LoadProfilePort
import com.seugi.api.domain.profile.application.port.out.SaveProfilePort
import com.seugi.api.global.exception.CustomException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ProfileAdapter(
    private val memberRepository: MemberRepository,
    private val profileRepository: ProfileRepository,
    private val profileMapper: ProfileMapper
) : LoadProfilePort, SaveProfilePort, ExistProfilePort {

    override fun loadProfile(memberId: Long, workspaceId: String): Profile {
        val memberEntity = memberRepository.findByIdOrNull(memberId)
            ?: throw CustomException(MemberErrorCode.MEMBER_NOT_FOUND)

        val profileEntity = profileRepository.findByMemberAndWorkspaceId(memberEntity, workspaceId)
            ?: throw CustomException(ProfileErrorCode.PROFILE_NOT_FOUND)

        return profileMapper.toDomain(profileEntity)
    }

    override fun saveProfile(profile: Profile) {
        profileRepository.save(
            profileMapper.toEntity(profile)
        )
    }

    override fun existProfile(memberId: Long, workspaceId: String): Boolean {
        val memberEntity = memberRepository.findByIdOrNull(memberId)
            ?: throw CustomException(MemberErrorCode.MEMBER_NOT_FOUND)

        return profileRepository.existsByMemberAndWorkspaceId(memberEntity, workspaceId)
    }

}