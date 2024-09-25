package com.seugi.api.domain.oauth.application.service

import com.seugi.api.domain.member.application.port.out.LoadMemberPort
import com.seugi.api.domain.oauth.adapter.`in`.dto.request.GoogleCodeRequest
import com.seugi.api.domain.oauth.application.exception.OAuthErrorCode
import com.seugi.api.domain.oauth.application.model.OAuth
import com.seugi.api.domain.oauth.port.`in`.GoogleConnUseCase
import com.seugi.api.domain.oauth.port.out.ExistOAuthPort
import com.seugi.api.domain.oauth.port.out.SaveOAuthPort
import com.seugi.api.global.auth.oauth.google.GoogleProperties
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.infra.oauth.google.GoogleClient
import com.seugi.api.global.infra.oauth.google.req.GoogleExchangeRequest
import com.seugi.api.global.response.BaseResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Service
class GoogleConnService (
    private val existOAuthPort: ExistOAuthPort,
    private val client: GoogleClient,
    private val properties: GoogleProperties,
    private val loadMemberPort: LoadMemberPort,
    private val saveOAuthPort: SaveOAuthPort,
) : GoogleConnUseCase {

    @Transactional
    override fun connect(id: Long, dto: GoogleCodeRequest): BaseResponse<Unit> {
        if (existOAuthPort.existOAuth(id, "google")) {
            throw CustomException(OAuthErrorCode.OAUTH_ALREADY_EXIST)
        }

        val decode = URLDecoder.decode(dto.code, StandardCharsets.UTF_8)
        val exchange = client.exchange(GoogleExchangeRequest(decode, properties))
        val parse = client.parse(exchange.idToken)

        val member = loadMemberPort.loadMemberWithId(id)
        val oauth = OAuth("google", parse.sub, exchange.accessToken, exchange.refreshToken, member)
        saveOAuthPort.saveOAuth(oauth)

        return BaseResponse (
            message = "구글 연동 성공 !!"
        )
    }

}