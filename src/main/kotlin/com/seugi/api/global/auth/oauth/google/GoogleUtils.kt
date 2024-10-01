package com.seugi.api.global.auth.oauth.google

import com.seugi.api.global.common.enums.Platform
import com.seugi.api.global.infra.oauth.google.GoogleClient
import com.seugi.api.global.infra.oauth.google.req.GoogleExchangeRequest
import com.seugi.api.global.infra.oauth.google.res.GoogleExchangeResponse
import com.seugi.api.global.infra.oauth.google.res.GoogleParseResponse
import org.springframework.stereotype.Component

@Component
class GoogleUtils (
    private val client: GoogleClient,
    private val properties: GoogleProperties
) {

    fun getRedirectUri(platform: Platform): String {
        return when (platform) {
            Platform.ANDROID -> ""
            Platform.IOS -> ""
            Platform.WEB -> properties.redirectUri
        }
    }

    fun exchange(code: String, redirectUri: String): GoogleExchangeResponse {
        return client.exchange(GoogleExchangeRequest(code, redirectUri, properties))
    }

    fun parse(idToken: String): GoogleParseResponse {
        return client.parse(idToken)
    }

}
