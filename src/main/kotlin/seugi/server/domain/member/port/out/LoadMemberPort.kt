package seugi.server.domain.member.port.out

import seugi.server.domain.member.application.model.Member

interface LoadMemberPort {

    fun loadMemberWithId(id: Long): Member

    fun loadMemberWithEmail(email: String): Member

}