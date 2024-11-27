package com.seugi.api.domain.member.presentation.controller.dto.res

import com.seugi.api.domain.member.domain.model.Member

data class RetrieveMemberResponse (

    val id: Long,
    val email: String,
    val birth: String,
    val name: String,
    val picture: String?,

) {
    
    constructor (member: Member) : this (
        id = member.id,
        email = member.email,
        birth = member.birth,
        name = member.name,
        picture = member.picture
    )

}