package com.seugi.api.domain.oauth.adapter.out.repository

import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import com.seugi.api.domain.oauth.adapter.out.entity.OAuthEntity

interface OAuthCustomRepository {

    fun findByMemberIdAndProvider(id: MemberEntity, provider: String): OAuthEntity?
    fun existsByMemberIdAndProvider(id: MemberEntity, provider: String): Boolean

}