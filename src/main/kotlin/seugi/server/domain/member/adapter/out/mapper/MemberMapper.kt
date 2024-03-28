package seugi.server.domain.member.adapter.out.mapper

import org.springframework.stereotype.Component
import seugi.server.domain.member.adapter.out.entity.MemberEntity
import seugi.server.domain.member.application.model.Member
import seugi.server.domain.member.application.model.value.*
import seugi.server.global.common.Mapper

@Component
class MemberMapper: Mapper<Member, MemberEntity> {

    override fun toDomain(entity: MemberEntity): Member {
        return Member (
            id = MemberId(entity.id),
            name = MemberName(entity.name),
            email = MemberEmail(entity.email),
            password = MemberPassword(entity.password),
            birth = MemberBirth(entity.birth),
            role = MemberRole(entity.role),
            loginId = MemberLoginId(entity.loginId),
            provider = MemberProvider(entity.provider),
            providerId = MemberProviderId(entity.providerId)
        )
    }

    override fun toEntity(domain: Member): MemberEntity {
        return MemberEntity(
            name = domain.name.value,
            email = domain.email.value,
            password = domain.password.value,
            birth = domain.birth.value,
            loginId = domain.loginId.value,
            provider = domain.provider.value,
            providerId = domain.providerId.value
        )
    }

}