package seugi.server.domain.member.port.out

import java.lang.reflect.Member

interface SaveMemberPort {

    fun saveMember(member: Member)

}