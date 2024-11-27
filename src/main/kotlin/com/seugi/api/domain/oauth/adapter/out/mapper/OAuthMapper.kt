package com.seugi.api.domain.oauth.adapter.out.mapper

import com.seugi.api.domain.member.domain.mapper.MemberMapper
import com.seugi.api.domain.oauth.adapter.out.entity.OAuthEntity
import com.seugi.api.domain.oauth.application.model.OAuth
import com.seugi.api.domain.oauth.application.model.vo.*
import com.seugi.api.global.common.Mapper
import org.springframework.stereotype.Component

@Component
class OAuthMapper (
    private val memberMapper: MemberMapper
) : Mapper<OAuth, OAuthEntity> {

    override fun toDomain(entity: OAuthEntity): OAuth {
        return OAuth (
            id = OAuthId(entity.id),
            member = memberMapper.toDomain(entity.member),
            provider = OAuthProvider(entity.provider),
            sub = OAuthSub(entity.sub),
            accessToken = OAuthAccessToken(entity.accessToken),
            refreshToken = OAuthRefreshToken(entity.refreshToken)
        )
    }

    override fun toEntity(domain: OAuth): OAuthEntity {
        return OAuthEntity (
            id = domain.id.value,
            member = memberMapper.toEntity(domain.member),
            provider = domain.provider.value,
            sub = domain.sub.value,
            accessToken = domain.accessToken.value,
            refreshToken = domain.refreshToken.value
        )
    }

}