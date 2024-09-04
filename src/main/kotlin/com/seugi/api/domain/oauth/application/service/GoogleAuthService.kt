package com.seugi.api.domain.oauth.application.service

import com.seugi.api.domain.member.application.model.Member
import com.seugi.api.domain.member.application.port.out.ExistMemberPort
import com.seugi.api.domain.member.application.port.out.LoadMemberPort
import com.seugi.api.domain.member.application.port.out.SaveMemberPort
import com.seugi.api.domain.oauth.adapter.`in`.dto.GoogleAuthRequest
import com.seugi.api.domain.oauth.application.model.OAuth
import com.seugi.api.domain.oauth.port.`in`.GoogleAuthUseCase
import com.seugi.api.domain.oauth.port.out.SaveOAuthPort
import com.seugi.api.global.auth.jwt.JwtInfo
import com.seugi.api.global.auth.jwt.JwtUtils
import com.seugi.api.global.auth.oauth.GoogleProperties
import com.seugi.api.global.infra.oauth.google.exchange.GoogleExchangeClient
import com.seugi.api.global.infra.oauth.google.exchange.GoogleExchangeRequest
import com.seugi.api.global.infra.oauth.google.parse.GoogleParseClient
import com.seugi.api.global.response.BaseResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Service
class GoogleAuthService (
    private val properties: GoogleProperties,
    private val exchangeClient: GoogleExchangeClient,
    private val parseClient: GoogleParseClient,
    private val saveOAuthPort: SaveOAuthPort,
    private val existMemberPort: ExistMemberPort,
    private val loadMemberPort: LoadMemberPort,
    private val saveMemberPort: SaveMemberPort,
    private val jwtUtils: JwtUtils,
) : GoogleAuthUseCase {

    @Transactional
    override fun authenticate(dto: GoogleAuthRequest): BaseResponse<JwtInfo> {
        val decode = URLDecoder.decode(dto.code, StandardCharsets.UTF_8)
        val exchange = exchangeClient.exchange(GoogleExchangeRequest(decode, properties))
        val parse = parseClient.parse(exchange.idToken)

        if (!existMemberPort.existMemberWithEmail(parse.email)) {
            val model = Member(parse)
            val member = saveMemberPort.saveMember(model)

            val oauth = OAuth(exchange, member)
            saveOAuthPort.saveOAuth(oauth)

            return BaseResponse (
                message = "구글 가입 성공 !!",
                data = jwtUtils.generate(member)
            )
        }

        return BaseResponse (
            message = "구글 로그인 성공 !!",
            data = jwtUtils.generate(
                loadMemberPort.loadMemberWithEmail(parse.email)
            )
        )
    }

}