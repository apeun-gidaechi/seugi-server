package seugi.server.domain.member.application.exception

import org.springframework.http.HttpStatus
import seugi.server.global.exception.CustomErrorCode

enum class MemberErrorCode (
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
) : CustomErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER-001", "멤버를 찾을 수 없네"),
    MEMBER_ALREADY_EXIST(HttpStatus.CONFLICT, "MEMBER-002", "멤버가 이미 존재한다"),
    MEMBER_NOT_SUFFICIENT(HttpStatus.PARTIAL_CONTENT, "MEMBER-003", "멤버의 정보가 부족하다")

}