package com.seugi.api.domain.oauth.application.service

import com.google.api.client.auth.oauth2.TokenResponseException
import com.google.api.client.json.gson.GsonFactory
import com.seugi.api.global.auth.oauth.google.GoogleProperties
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest
import com.google.api.client.http.javanet.NetHttpTransport
import com.seugi.api.domain.oauth.application.exception.OAuthErrorCode
import com.seugi.api.domain.oauth.application.model.OAuth
import com.seugi.api.domain.oauth.application.model.vo.OAuthAccessToken
import com.seugi.api.domain.oauth.port.out.SaveOAuthPort
import com.seugi.api.global.exception.CustomException
import org.springframework.stereotype.Service

@Service
class GoogleRefreshService (
    private val properties: GoogleProperties,
    private val netHttpTransport: NetHttpTransport,
    private val saveOAuthPort: SaveOAuthPort
) {

    fun refreshAccessToken(oauth: OAuth) {
        try {
            val accessToken = GoogleRefreshTokenRequest (
                netHttpTransport,
                GsonFactory.getDefaultInstance(),
                oauth.refreshToken.value,
                properties.clientId,
                properties.clientSecret
            ).execute().accessToken

            oauth.accessToken = OAuthAccessToken(accessToken)

            saveOAuthPort.saveOAuth(oauth)
        } catch (e: TokenResponseException) {
            throw CustomException(OAuthErrorCode.OAUTH_REFRESH_EXPIRED)
        }

    }

}