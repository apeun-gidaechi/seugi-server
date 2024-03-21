package seugi.server.domain.member.adapter.out.mapper

import org.springframework.stereotype.Component
import seugi.server.domain.member.adapter.out.entity.MemberEntity
import seugi.server.domain.member.application.model.Member

@Component
class MemberMapper {

    fun returnMemberToMemberEntityWithoutId(member: Member): MemberEntity {
        return MemberEntity(
            id = null,
            name = member.name.value,
            email = member.email.value,
            password = member.password.value,
            birth = member.birth.value
        )
    }

}