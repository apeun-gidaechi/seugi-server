package seugi.server.global.auth.jwt

data class JwtInfo (

    val accessToken: String,

    val refreshToken: String

)