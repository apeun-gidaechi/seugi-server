package com.seugi.api.domain.oauth.adapter.out

import com.seugi.api.domain.member.domain.MemberRepository
import com.seugi.api.domain.member.exception.MemberErrorCode
import com.seugi.api.domain.oauth.adapter.out.mapper.OAuthMapper
import com.seugi.api.domain.oauth.adapter.out.repository.OAuthRepository
import com.seugi.api.domain.oauth.application.exception.OAuthErrorCode
import com.seugi.api.domain.oauth.application.model.OAuth
import com.seugi.api.domain.oauth.port.out.DeleteOAuthPort
import com.seugi.api.domain.oauth.port.out.ExistOAuthPort
import com.seugi.api.domain.oauth.port.out.LoadOAuthPort
import com.seugi.api.domain.oauth.port.out.SaveOAuthPort
import com.seugi.api.global.auth.oauth.enums.Provider
import com.seugi.api.global.exception.CustomException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class OAuthAdapter (
    private val oAuthRepository: OAuthRepository,
    private val memberRepository: MemberRepository,
    private val oAuthMapper: OAuthMapper
): LoadOAuthPort, ExistOAuthPort, SaveOAuthPort, DeleteOAuthPort {

    override fun loadOAuthByMemberIdAndProvider(memberId: Long, provider: Provider): OAuth {
        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw CustomException(MemberErrorCode.NOT_FOUND)

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
            ?: throw  CustomException(MemberErrorCode.NOT_FOUND)

        return oAuthRepository.existsByMemberAndProvider(member, provider)
    }

    override fun saveOAuth(oauth: OAuth) {
        oAuthRepository.save(
            oAuthMapper.toEntity(oauth)
        )
    }

    override fun deleteOAuth(memberId: Long, provider: Provider) {
        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw CustomException(MemberErrorCode.NOT_FOUND)

        oAuthRepository.deleteByMemberAndProvider(member, provider)
    }
}