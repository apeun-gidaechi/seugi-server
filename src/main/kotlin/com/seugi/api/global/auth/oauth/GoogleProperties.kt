package com.seugi.api.global.auth.oauth

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class GoogleProperties (

    @Value("\${oauth.google.client-id}") val clientId: String,
    @Value("\${oauth.google.client-secret}") val clientSecret: String,
    @Value("\${oauth.google.redirect-uri}") val redirectUri: String

)