package com.seugi.api.domain.member.application.port.out

import com.seugi.api.domain.member.application.model.Member

interface SaveMemberPort {

    fun saveMember(member: Member): Member

}