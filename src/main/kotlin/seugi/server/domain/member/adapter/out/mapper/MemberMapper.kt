package seugi.server.domain.member.adapter.out.mapper

import org.springframework.stereotype.Component
import seugi.server.domain.member.adapter.out.entity.MemberEntity
import seugi.server.domain.member.application.model.Member
import seugi.server.domain.member.application.model.value.*

@Component
class MemberMapper {
    fun returnMemberToMemberEntityWithoutId(member: Member): MemberEntity {
        return MemberEntity(
            name = member.name.value,
            email = member.email.value,
            password = member.password.value,
            birth = member.birth.value,
        )
    }

    fun returnMemberEntityToMember(memberEntity: MemberEntity): Member {
        return Member (
            id = MemberId(memberEntity.id),
            name = MemberName(memberEntity.name),
            email = MemberEmail(memberEntity.email),
            password = MemberPassword(memberEntity.password),
            birth = MemberBirth(memberEntity.birth),
            role = MemberRole(memberEntity.role)
        )
    }

}