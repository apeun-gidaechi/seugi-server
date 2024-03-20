package seugi.server.domain.member.adapter.out.mapper

import org.springframework.stereotype.Component
import seugi.server.domain.member.adapter.out.entity.MemberEntity
import java.lang.reflect.Member

@Component
class MemberMapper {

    fun returnMemberToMemberEntityWithoutId(member: Member): MemberEntity {
        return MemberEntity(
            id = null
        )
    }

}