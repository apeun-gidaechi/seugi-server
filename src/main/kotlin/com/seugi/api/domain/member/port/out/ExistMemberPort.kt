package com.seugi.api.domain.member.port.out


interface ExistMemberPort {

    fun existMemberWithEmail(email: String): Boolean

}