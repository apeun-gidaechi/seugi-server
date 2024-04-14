package com.seugi.api.domain.member.port.out

import com.seugi.api.domain.member.application.model.Member

interface LoadMemberPort {

    fun loadMemberWithId(id: Long): Member

    fun loadMemberWithEmail(email: String): Member

}