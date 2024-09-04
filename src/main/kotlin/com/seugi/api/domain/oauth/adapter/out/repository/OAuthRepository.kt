package com.seugi.api.domain.oauth.adapter.out.repository

import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import com.seugi.api.domain.oauth.adapter.out.entity.OAuthEntity
import org.springframework.data.repository.CrudRepository

interface OAuthRepository : CrudRepository<OAuthEntity, Long>, OAuthCustomRepository {

    override fun findByMemberIdAndProvider(id: MemberEntity, provider: String): OAuthEntity?
    override fun existsByMemberIdAndProvider(id: MemberEntity, provider: String): Boolean

}