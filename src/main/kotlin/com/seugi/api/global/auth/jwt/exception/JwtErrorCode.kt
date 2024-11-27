package com.seugi.api.global.auth.jwt.exception

import com.seugi.api.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus

enum class JwtErrorCode (
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
): CustomErrorCode {

    EXPIRED(HttpStatus.UNAUTHORIZED, "FORBIDDEN", "토큰이 만료되었어요"),
    SIGNATURE_NOT_MATCH(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "토큰의 서명이 일치하지 않아요"),
    MALFORMED_STRUCT(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "토큰이 구조가 이상하거나 데이터가 일치하지 않아요"),
    UNSUPPORTED_TYPE(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "지원하지 않는 토큰이에요"),
    ILLEGAL_ARGUMENT(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "토큰 처리 과정에서 오류가 발생했어요"),
    UNKNOWN(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "토큰 처리 과정에서 알 수 없는 오류가 발생했어요"),
}