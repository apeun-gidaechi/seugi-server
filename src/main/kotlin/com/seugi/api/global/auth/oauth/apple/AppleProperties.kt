package com.seugi.api.global.auth.oauth.apple

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "oauth.apple")
data class AppleProperties @ConstructorBinding constructor(

    val appId: String,
    val serviceId: String,
    val keyId: String,
    val teamId: String,
    val privateKey: String,
    val grantType: String,
    val baseUrl: String,
    val redirectUri: String

)
