package com.seugi.api.domain.member.adapter.`in`.dto.res

import com.seugi.api.domain.member.application.model.Member

data class RetrieveMemberResponse (

    val id: Long,
    val email: String,
    val birth: String,
    val name: String,
    val picture: String,

) {
    companion object {

        fun toResponse(member: Member): RetrieveMemberResponse {
            return RetrieveMemberResponse (
                id = member.id!!.value,
                email = member.email.value,
                birth = member.birth.value,
                name = member.name.value,
                picture = member.picture.value
            )
        }

    }
}