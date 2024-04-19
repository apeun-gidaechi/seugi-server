package com.seugi.api.domain.workspace.exception

import com.seugi.api.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus


enum class WorkspaceErrorCode (
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
): CustomErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "워크스페이스를 찾을 수 없습니다."),
    NOT_MATCH(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "워크스페이스 코드가 알맞지 않습니다.")

}