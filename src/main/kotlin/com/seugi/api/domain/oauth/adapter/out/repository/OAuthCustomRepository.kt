package com.seugi.api.domain.oauth.adapter.out.repository

import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import com.seugi.api.domain.oauth.adapter.out.entity.OAuthEntity

interface OAuthCustomRepository {

    fun findByMemberAndProvider(member: MemberEntity, provider: String): OAuthEntity?
    fun findByProviderAndSub(provider: String, sub: String): OAuthEntity?
    fun existsByMemberAndProvider(member: MemberEntity, provider: String): Boolean

}