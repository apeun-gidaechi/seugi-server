package com.seugi.api.global.auth.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties @ConstructorBinding constructor (

    val secretKey: String,
    val accessExpire: Long,
    val refreshExpire: Long

)