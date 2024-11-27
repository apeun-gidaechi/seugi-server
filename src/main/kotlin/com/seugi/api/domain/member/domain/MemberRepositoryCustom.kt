package com.seugi.api.domain.member.domain

interface MemberRepositoryCustom {

    fun findByEmail(email: String): MemberEntity?
    fun existsByEmail(email: String): Boolean

}