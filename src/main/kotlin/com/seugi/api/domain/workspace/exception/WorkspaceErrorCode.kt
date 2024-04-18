package com.seugi.api.domain.workspace.exception

import com.seugi.api.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus


enum class WorkspaceErrorCode (
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
): CustomErrorCode {

    NOT_FOUND(HttpStatus.FOUND, "NOT_FOUND", "워크스페이스를 찾을 수 없습니다.")

}