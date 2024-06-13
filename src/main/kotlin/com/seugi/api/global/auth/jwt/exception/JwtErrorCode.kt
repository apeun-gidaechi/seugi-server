package com.seugi.api.global.auth.jwt.exception

import com.seugi.api.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus

enum class JwtErrorCode (
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
): CustomErrorCode {

    JWT_TOKEN_EXPIRED(HttpStatus.FORBIDDEN, "FORBIDDEN", "토큰이 만료되었어요"),
    JWT_TOKEN_SIGNATURE_ERROR(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "토큰의 서명이 일치하지 않아요"),
    JWT_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "토큰이 구조가 이상하거나 데이터가 일치하지 않아요"),
    JWT_MEMBER_NOT_MATCH(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "비밀번호가 일치하지 않아요"),
    JWT_NULL_EXCEPTION(HttpStatus.SERVICE_UNAVAILABLE, "SERVICE_UNAVAILABLE", "알 수 없는 오류가 발생했어요"),

}