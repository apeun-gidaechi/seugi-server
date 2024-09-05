package com.seugi.api.domain.member.application.exception

import org.springframework.http.HttpStatus
import com.seugi.api.global.exception.CustomErrorCode

enum class MemberErrorCode (
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
) : CustomErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "멤버를 찾을 수 없습니다"),
    MEMBER_ALREADY_EXIST(HttpStatus.CONFLICT, "CONFLICT", "멤버가 이미 존재합니다"),

}