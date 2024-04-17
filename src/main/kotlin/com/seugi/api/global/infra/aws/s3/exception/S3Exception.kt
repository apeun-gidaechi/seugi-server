package com.seugi.api.global.infra.aws.s3.exception

import com.seugi.api.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus

enum class S3Exception (
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
): CustomErrorCode {

    FILE_UPLOAD_FAIL(HttpStatus.EXPECTATION_FAILED, "F2", "파일 업로드에 실패하였습니다."),
    FILE_EMPTY(HttpStatus.BAD_REQUEST, "F3", "빈 파일입니다.")

}