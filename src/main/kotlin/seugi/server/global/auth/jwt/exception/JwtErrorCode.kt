package seugi.server.global.auth.jwt.exception

import org.springframework.http.HttpStatus
import seugi.server.global.exception.CustomErrorCode

enum class JwtErrorCode (
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
): CustomErrorCode {

    JWT_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "LOGIN-001", "토큰 만료다"),
    JWT_MEMBER_NOT_MATCH(HttpStatus.UNAUTHORIZED, "LOGIN-002", "비밀번호 일치 노노"),
    JWT_NULL_EXCEPTION(HttpStatus.SERVICE_UNAVAILABLE, "LOGIN-003", "어떻게 지평좌표계로 고정을 하셨죠?")

}