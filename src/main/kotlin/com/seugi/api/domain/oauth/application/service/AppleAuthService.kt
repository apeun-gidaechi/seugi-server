package com.seugi.api.domain.oauth.application.service

import com.seugi.api.domain.member.domain.MemberRepository
import com.seugi.api.domain.member.domain.model.Member
import com.seugi.api.domain.member.service.MemberService
import com.seugi.api.domain.oauth.adapter.`in`.dto.request.AppleCodeRequest
import com.seugi.api.domain.oauth.application.model.OAuth
import com.seugi.api.domain.oauth.port.`in`.AppleAuthUseCase
import com.seugi.api.domain.oauth.port.out.SaveOAuthPort
import com.seugi.api.global.auth.jwt.JwtInfo
import com.seugi.api.global.auth.jwt.JwtUtils
import com.seugi.api.global.auth.oauth.apple.AppleUtils
import com.seugi.api.global.auth.oauth.enums.Provider
import com.seugi.api.global.response.BaseResponse
import org.springframework.stereotype.Service

@Service
class AppleAuthService (
    private val appleUtils: AppleUtils,
    private val jwtUtils: JwtUtils,
    private val saveOAuthPort: SaveOAuthPort,
    private val memberRepository: MemberRepository,
    private val memberService: MemberService
): AppleAuthUseCase {

    override fun authenticate(dto: AppleCodeRequest): BaseResponse<JwtInfo> {
        val clientId: String = appleUtils.getClientId(dto.platform)

        val exchange = appleUtils.exchange(dto.code, clientId)

        val headers = appleUtils.parseHeader(exchange.idToken)
        val keys = appleUtils.getPublicKeys()
        val publicKey = appleUtils.generate(headers, keys)
        val claims = appleUtils.extractClaims(exchange.idToken, publicKey)

        val email = claims["email"] as String

        if (!memberRepository.existsByEmail(email)) {
            val model = Member(dto.name, dto.token, email)
            val member = memberService.save(model)

            val oauth = OAuth(
                Provider.APPLE,
                claims.subject,
                exchange.accessToken,
                exchange.refreshToken!!,
                member
            )
            saveOAuthPort.saveOAuth(oauth)

            return BaseResponse(
                message = "애플 가입 성공 !!",
                data = jwtUtils.generate(member)
            )
        }

        val member = memberService.findByEmail(email)
        member.fcmToken.add(dto.token)
        memberService.save(member)

        return BaseResponse(
            message = "애플 로그인 성공 !!",
            data = jwtUtils.generate(member)
        )
    }
}