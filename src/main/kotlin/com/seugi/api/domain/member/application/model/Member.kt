package com.seugi.api.domain.member.application.model

import com.seugi.api.domain.member.adapter.`in`.dto.req.EditMemberRequest
import com.seugi.api.domain.member.adapter.`in`.dto.req.RegisterMemberRequest
import com.seugi.api.domain.member.application.model.value.*

data class Member (

    val id: MemberId?,
    var name: MemberName,
    val email: MemberEmail,
    var picture: MemberPicture,
    val password: MemberPassword,
    var birth: MemberBirth,
    val role: MemberRole,
    val loginId: MemberLoginId,
    val provider: MemberProvider,
    val providerId: MemberProviderId,
    var deleted: MemberDeleted

) {

    constructor(dto: RegisterMemberRequest, encrypted: String) : this (
        id = MemberId(0),
        name = MemberName(dto.name),
        email = MemberEmail(dto.email),
        picture = MemberPicture(""),
        password = MemberPassword(encrypted),
        birth =  MemberBirth(""),
        role = MemberRole("ROLE_USER"),
        loginId = MemberLoginId(""),
        provider = MemberProvider(""),
        providerId = MemberProviderId(""),
        deleted = MemberDeleted(false)
    )

    fun editMember (dto: EditMemberRequest) {
        this.picture = MemberPicture(dto.picture)
        this.name = MemberName(dto.name)
        this.birth = MemberBirth(dto.birth)
    }

}