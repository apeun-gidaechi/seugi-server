package com.seugi.api.domain.oauth.adapter.out.repository

import com.seugi.api.domain.member.domain.MemberEntity
import com.seugi.api.domain.oauth.adapter.out.entity.OAuthEntity
import com.seugi.api.global.auth.oauth.enums.Provider

interface OAuthCustomRepository {

    fun findByMemberAndProvider(member: MemberEntity, provider: Provider): OAuthEntity?
    fun findByProviderAndSub(provider: Provider, sub: String): OAuthEntity?
    fun existsByMemberAndProvider(member: MemberEntity, provider: Provider): Boolean
    fun deleteByMemberAndProvider(member: MemberEntity, provider: Provider)

}