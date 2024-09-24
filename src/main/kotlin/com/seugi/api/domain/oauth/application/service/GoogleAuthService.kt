package com.seugi.api.domain.oauth.application.service

import com.seugi.api.domain.member.application.model.Member
import com.seugi.api.domain.member.application.port.out.ExistMemberPort
import com.seugi.api.domain.member.application.port.out.LoadMemberPort
import com.seugi.api.domain.member.application.port.out.SaveMemberPort
import com.seugi.api.domain.oauth.adapter.`in`.dto.request.GoogleCodeRequest
import com.seugi.api.domain.oauth.application.model.OAuth
import com.seugi.api.domain.oauth.port.`in`.GoogleAuthUseCase
import com.seugi.api.domain.oauth.port.out.SaveOAuthPort
import com.seugi.api.global.auth.jwt.JwtInfo
import com.seugi.api.global.auth.jwt.JwtUtils
import com.seugi.api.global.auth.oauth.google.GoogleProperties
import com.seugi.api.global.infra.oauth.google.GoogleClient
import com.seugi.api.global.infra.oauth.google.req.GoogleExchangeRequest
import com.seugi.api.global.response.BaseResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Service
class GoogleAuthService (
    private val properties: GoogleProperties,
    private val client: GoogleClient,
    private val saveOAuthPort: SaveOAuthPort,
    private val existMemberPort: ExistMemberPort,
    private val loadMemberPort: LoadMemberPort,
    private val saveMemberPort: SaveMemberPort,
    private val jwtUtils: JwtUtils,
) : GoogleAuthUseCase {

    @Transactional
    override fun authenticate(dto: GoogleCodeRequest): BaseResponse<JwtInfo> {
        val decode = URLDecoder.decode(dto.code, StandardCharsets.UTF_8)
        val exchange = client.exchange(GoogleExchangeRequest(decode, properties))
        val parse = client.parse(exchange.idToken)

        if (!existMemberPort.existMemberWithEmail(parse.email)) {
            val model = Member(parse.name, parse.email)
            val member = saveMemberPort.saveMember(model)

            val oauth = OAuth("google", parse.sub, exchange.accessToken, exchange.refreshToken, member)
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