package com.seugi.api.domain.member.service

import com.seugi.api.domain.member.presentation.controller.dto.req.EditMemberRequest
import com.seugi.api.domain.member.presentation.controller.dto.req.LoginMemberRequest
import com.seugi.api.domain.member.presentation.controller.dto.req.RegisterMemberRequest
import com.seugi.api.domain.member.presentation.controller.dto.res.RetrieveMemberResponse
import com.seugi.api.domain.member.domain.model.Member
import com.seugi.api.global.auth.jwt.JwtInfo

interface MemberService {

    fun save(model: Member): Member
    fun findById(id: Long): Member
    fun findByIdOrNull(id: Long): Member?
    fun findByEmail(email: String): Member
    fun findByEmailOrNull(email: String): Member?
    fun register(dto: RegisterMemberRequest): JwtInfo
    fun login(dto: LoginMemberRequest): JwtInfo
    fun retrieve(model: Member): RetrieveMemberResponse
    fun refresh(token: String): String
    fun edit(dto: EditMemberRequest, model: Member)
    fun logout(fcmToken: String, model: Member)
    fun delete(model: Member)

}