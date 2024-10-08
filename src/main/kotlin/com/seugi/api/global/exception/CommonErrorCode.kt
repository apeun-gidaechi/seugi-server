package com.seugi.api.global.exception

import org.springframework.http.HttpStatus

enum class CommonErrorCode(
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
) : CustomErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "서버 오류입니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "요청을 잘못하였습니다, 요청을 다시 확인해주세요."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "요청을 찾을 수 없습니다."),
    FORBIDDEN_REQUEST(HttpStatus.FORBIDDEN, "FORBIDDEN", "요청에 대한 권한이 없습니다.")

}