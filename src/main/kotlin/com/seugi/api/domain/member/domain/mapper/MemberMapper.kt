package com.seugi.api.domain.member.domain.mapper

import com.seugi.api.domain.member.domain.MemberEntity
import com.seugi.api.domain.member.domain.model.Member
import com.seugi.api.global.common.Mapper
import org.springframework.stereotype.Component

@Component
class MemberMapper : Mapper<Member, MemberEntity> {

    override fun toDomain(entity: MemberEntity): Member {
        return Member (
            id = entity.id,
            name = entity.name,
            email = entity.email,
            picture = entity.picture,
            password = entity.password,
            birth = entity.birth,
            role = entity.role,
            deleted = entity.deleted,
            fcmToken = entity.fcmToken
        )
    }

    override fun toEntity(domain: Member): MemberEntity {
        return MemberEntity (
            id = domain.id,
            name = domain.name,
            email = domain.email,
            picture = domain.picture,
            password = domain.password,
            birth = domain.birth,
            role = domain.role,
            deleted = domain.deleted,
            fcmToken = domain.fcmToken
        )
    }

}