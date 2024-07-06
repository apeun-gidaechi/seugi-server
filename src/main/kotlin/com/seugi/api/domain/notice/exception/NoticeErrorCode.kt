package com.seugi.api.domain.notice.exception

import com.seugi.api.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus

enum class NoticeErrorCode(
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
) : CustomErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "공지를 찾을 수 없습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN", "요청에 대한 권한이 없습니다.")

}