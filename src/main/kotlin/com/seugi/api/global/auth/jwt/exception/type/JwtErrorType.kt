package com.seugi.api.global.auth.jwt.exception.type

enum class JwtErrorType {
    OK,
    ExpiredJwtException,
    SignatureException,
    MalformedJwtException,
    UnsupportedJwtException,
    IllegalArgumentException,
    UNKNOWN_EXCEPTION
}