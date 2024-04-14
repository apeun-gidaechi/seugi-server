package com.seugi.api.domain.member.port.out

import com.seugi.api.domain.member.application.model.Member


interface SaveMemberPort {

    fun saveMember(member: Member)

}