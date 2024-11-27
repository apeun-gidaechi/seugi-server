package com.seugi.api.domain.member.exception

import org.springframework.http.HttpStatus
import com.seugi.api.global.exception.CustomErrorCode

enum class MemberErrorCode (
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
) : CustomErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "멤버를 찾을 수 없습니다"),
    ALREADY_EXIST(HttpStatus.CONFLICT, "CONFLICT", "멤버가 이미 존재합니다"),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "비밀번호가 일치하지 않습니다")

}