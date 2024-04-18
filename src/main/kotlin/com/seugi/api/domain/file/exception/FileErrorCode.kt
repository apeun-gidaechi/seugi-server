package com.seugi.api.domain.file.exception

import com.seugi.api.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus

enum class FileErrorCode (
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
): CustomErrorCode {
    MEDIA_TYPE_ERROR(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "F2", "올바른 파일을 올려주세요"),
}