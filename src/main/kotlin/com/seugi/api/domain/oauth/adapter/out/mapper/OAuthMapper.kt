package com.seugi.api.domain.oauth.adapter.out.mapper

import com.seugi.api.domain.member.adapter.out.mapper.MemberMapper
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
            memberId = memberMapper.toDomain(entity.memberId),
            provider = OAuthProvider(entity.provider),
            accessToken = OAuthAccessToken(entity.accessToken),
            refreshToken = OAuthRefreshToken(entity.refreshToken)
        )
    }

    override fun toEntity(domain: OAuth): OAuthEntity {
        return OAuthEntity (
            id = domain.id.value,
            memberId = memberMapper.toEntity(domain.memberId),
            provider = domain.provider.value,
            accessToken = domain.accessToken.value,
            refreshToken = domain.refreshToken.value
        )
    }

}