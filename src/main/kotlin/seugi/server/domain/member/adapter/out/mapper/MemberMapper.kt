package seugi.server.domain.member.adapter.out.mapper

import seugi.server.domain.member.adapter.out.entity.MemberEntity
import java.lang.reflect.Member

class MemberMapper {

    fun returnMemberToMemberEntityWithoutId(member: Member): MemberEntity {
        return MemberEntity(
            id = null
        )
    }

}