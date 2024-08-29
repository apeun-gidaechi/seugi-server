package com.seugi.api.domain.timetable.exception

import com.seugi.api.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus

enum class TimetableException(
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
) : CustomErrorCode {

    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN", "워크스페이스에 대한 권한이 없습니다.")

}