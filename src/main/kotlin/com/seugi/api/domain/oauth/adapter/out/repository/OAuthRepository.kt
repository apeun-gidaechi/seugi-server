package com.seugi.api.domain.oauth.adapter.out.repository

import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import com.seugi.api.domain.oauth.adapter.out.entity.OAuthEntity
import com.seugi.api.global.auth.oauth.enums.Provider
import org.springframework.data.repository.CrudRepository

interface OAuthRepository : CrudRepository<OAuthEntity, Long>, OAuthCustomRepository {

    override fun findByMemberAndProvider(member: MemberEntity, provider: Provider): OAuthEntity?
    override fun findByProviderAndSub(provider: Provider, sub: String): OAuthEntity?
    override fun existsByMemberAndProvider(member: MemberEntity, provider: Provider): Boolean

}