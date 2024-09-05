package com.seugi.api.domain.member.adapter.out.repository

import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import java.util.Optional

interface MemberRepositoryCustom {

    fun findByEmail(email: String): MemberEntity?
    fun existsByEmail(email: String): Boolean

}