package com.seugi.api.global.auth.jwt.exception

import org.springframework.http.HttpStatus
import com.seugi.api.global.exception.CustomErrorCode

enum class JwtErrorCode (
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
): CustomErrorCode {

    JWT_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "J1", "토큰이 만료되었어요"),
    JWT_MEMBER_NOT_MATCH(HttpStatus.UNAUTHORIZED, "J2", "비밀번호가 일치하지 않아요"),
    JWT_NULL_EXCEPTION(HttpStatus.SERVICE_UNAVAILABLE, "J3", "알 수 없는 오류가 발생했어요"),

}