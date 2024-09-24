package com.seugi.api.global.auth.oauth.google

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "oauth.google")
class GoogleProperties @ConstructorBinding constructor (

    val clientId: String,
    val clientSecret: String,
    val redirectUri: String,
    val grantType: String

)