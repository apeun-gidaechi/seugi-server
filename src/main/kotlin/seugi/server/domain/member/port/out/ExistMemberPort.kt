package seugi.server.domain.member.port.out

import seugi.server.domain.member.application.model.Member

interface ExistMemberPort {

    fun existMemberWithEmail(email: String): Boolean

}