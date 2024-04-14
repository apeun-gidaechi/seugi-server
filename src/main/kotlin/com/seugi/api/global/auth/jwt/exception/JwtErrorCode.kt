package com.seugi.api.global.auth.jwt.exception

import org.springframework.http.HttpStatus
import com.seugi.api.global.exception.CustomErrorCode

enum class JwtErrorCode (
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
): CustomErrorCode {

    JWT_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "J1", "토큰 만료다"),
    JWT_MEMBER_NOT_MATCH(HttpStatus.UNAUTHORIZED, "J2", "비밀번호 일치 노노"),
    JWT_NULL_EXCEPTION(HttpStatus.SERVICE_UNAVAILABLE, "J3", "어떻게 지평좌표계로 고정을 하셨죠?"),

}