package com.seugi.api.domain.member.adapter.out.mapper

import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import com.seugi.api.domain.member.application.model.Member
import com.seugi.api.domain.member.application.model.value.*
import com.seugi.api.global.common.Mapper
import org.springframework.stereotype.Component

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
            deleted = MemberDeleted(entity.deleted),
            fcmToken = MemberFCMToken(entity.tokenList)
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
            deleted = domain.deleted.value,
            tokenList = domain.fcmToken.token
        )
    }

}