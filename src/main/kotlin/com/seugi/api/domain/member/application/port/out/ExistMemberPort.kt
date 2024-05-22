package com.seugi.api.domain.member.application.port.out


interface ExistMemberPort {

    fun existMemberWithEmail(email: String): Boolean

}