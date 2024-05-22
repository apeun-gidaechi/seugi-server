package com.seugi.api.domain.member.adapter.out.mapper

import org.springframework.stereotype.Component
import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import com.seugi.api.domain.profile.adapter.out.entity.ProfileEntity
import com.seugi.api.domain.member.application.model.Member
import com.seugi.api.domain.member.application.model.value.*
import com.seugi.api.global.common.Mapper

@Component
class MemberMapper: Mapper<Member, MemberEntity> {

    override fun toDomain(entity: MemberEntity): Member {
        return Member (
            id = MemberId(entity.id!!),
            name = MemberName(entity.name),
            email = MemberEmail(entity.email),
            picture = MemberPicture(entity.picture),
            password = MemberPassword(entity.password),
            birth = MemberBirth(entity.birth),
            role = MemberRole(entity.role),
            loginId = MemberLoginId(entity.loginId),
            provider = MemberProvider(entity.provider),
            providerId = MemberProviderId(entity.providerId),
        )
    }

    override fun toEntity(domain: Member): MemberEntity {
        return MemberEntity (
            id = domain.id?.value ?: 0,
            name = domain.name.value,
            email = domain.email.value,
            picture = domain.picture.value,
            password = domain.password.value,
            birth = domain.birth.value,
            loginId = domain.loginId.value,
            provider = domain.provider.value,
            providerId = domain.providerId.value,
        )
    }

}