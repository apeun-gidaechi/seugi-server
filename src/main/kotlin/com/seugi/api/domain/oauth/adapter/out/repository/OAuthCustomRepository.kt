package com.seugi.api.domain.oauth.adapter.out.repository

import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import com.seugi.api.domain.oauth.adapter.out.entity.OAuthEntity
import com.seugi.api.global.common.enums.Provider

interface OAuthCustomRepository {

    fun findByMemberAndProvider(member: MemberEntity, provider: Provider): OAuthEntity?
    fun findByProviderAndSub(provider: Provider, sub: String): OAuthEntity?
    fun existsByMemberAndProvider(member: MemberEntity, provider: Provider): Boolean
    fun existsBySubAndProvider(sub: String, provider: Provider): Boolean

}