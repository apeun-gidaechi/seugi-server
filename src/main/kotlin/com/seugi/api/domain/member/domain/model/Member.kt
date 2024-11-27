package com.seugi.api.domain.member.domain.model

import com.seugi.api.domain.member.presentation.controller.dto.req.RegisterMemberRequest
import com.seugi.api.domain.member.domain.enums.MemberType

data class Member (

    val id: Long,
    var name: String,
    val email: String,
    var picture: String?,
    val password: String,
    var birth: String,
    val role: MemberType,
    var deleted: Boolean,
    val fcmToken: MutableSet<String>

) {

    constructor(dto: RegisterMemberRequest, encrypted: String) : this (
        id = 0,
        name = dto.name,
        email = dto.email,
        picture = "",
        password = encrypted,
        birth = "",
        role = MemberType.ROLE_USER,
        deleted = false,
        fcmToken = mutableSetOf()
    )

    constructor(name: String, token: String, email: String): this (
        id = 0,
        name = name,
        email = email,
        picture = "",
        password = "",
        birth = "",
        role = MemberType.ROLE_USER,
        deleted = false,
        fcmToken = mutableSetOf(token)
    )

}