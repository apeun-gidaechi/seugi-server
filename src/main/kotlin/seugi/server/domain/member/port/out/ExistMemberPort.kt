package seugi.server.domain.member.port.out


interface ExistMemberPort {

    fun existMemberWithEmail(email: String): Boolean

}