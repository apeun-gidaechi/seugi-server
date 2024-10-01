package com.seugi.api.domain.oauth.adapter.out

import com.seugi.api.domain.member.adapter.out.repository.MemberRepository
import com.seugi.api.domain.member.application.exception.MemberErrorCode
import com.seugi.api.domain.oauth.adapter.out.mapper.OAuthMapper
import com.seugi.api.domain.oauth.adapter.out.repository.OAuthRepository
import com.seugi.api.domain.oauth.application.exception.OAuthErrorCode
import com.seugi.api.domain.oauth.application.model.OAuth
import com.seugi.api.domain.oauth.port.out.ExistOAuthPort
import com.seugi.api.domain.oauth.port.out.LoadOAuthPort
import com.seugi.api.domain.oauth.port.out.SaveOAuthPort
import com.seugi.api.global.common.enums.Provider
import com.seugi.api.global.exception.CustomException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class OAuthAdapter (
    private val oAuthRepository: OAuthRepository,
    private val memberRepository: MemberRepository,
    private val oAuthMapper: OAuthMapper
): LoadOAuthPort, ExistOAuthPort, SaveOAuthPort {

    override fun loadOAuthByMemberIdAndProvider(memberId: Long, provider: Provider): OAuth {
        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw CustomException(MemberErrorCode.MEMBER_NOT_FOUND)

        val oAuth = oAuthRepository.findByMemberAndProvider(member, provider)
            ?: throw CustomException(OAuthErrorCode.OAUTH_NOT_FOUND)

        return oAuthMapper.toDomain(oAuth)
    }

    override fun loadOAuthByProviderAndSub(provider: Provider, sub: String): OAuth {
        val oAuth = oAuthRepository.findByProviderAndSub(provider, sub)
            ?: throw CustomException(OAuthErrorCode.OAUTH_NOT_FOUND)

        return oAuthMapper.toDomain(oAuth)
    }

    override fun existOAuthByMemberIdAndProvider(memberId: Long, provider: Provider): Boolean {
        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw  CustomException(MemberErrorCode.MEMBER_NOT_FOUND)

        return oAuthRepository.existsByMemberAndProvider(member, provider)
    }

    override fun existOAuthBySubAndProvider(sub: String, provider: Provider): Boolean {
        return oAuthRepository.existsBySubAndProvider(sub, provider)
    }

    override fun saveOAuth(oauth: OAuth) {
        oAuthRepository.save(
            oAuthMapper.toEntity(oauth)
        )
    }

}