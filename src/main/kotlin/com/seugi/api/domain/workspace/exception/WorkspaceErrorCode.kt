package com.seugi.api.domain.workspace.exception

import com.seugi.api.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus


enum class WorkspaceErrorCode (
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
): CustomErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "워크스페이스를 찾을 수 없습니다."),
    NOT_MATCH(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "워크스페이스 코드가 알맞지 않습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN", "요청에 대한 권한이 없습니다."),
    EXIST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "이미 유저가 워크스페이스에 존재합니다."),
    MEDIA_TYPE_ERROR(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "UNSUPPORTED_MEDIA_TYPE", "요청을 다시 확인해주세요."),

}