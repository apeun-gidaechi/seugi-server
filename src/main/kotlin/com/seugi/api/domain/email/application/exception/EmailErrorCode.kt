package com.seugi.api.domain.email.application.exception

import org.springframework.http.HttpStatus
import com.seugi.api.global.exception.CustomErrorCode

enum class EmailErrorCode (

    override val status: HttpStatus,
    override val state: String,
    override val message: String,

) : CustomErrorCode {

    CODE_NOT_EXIST(HttpStatus.NOT_FOUND, "EMAIL-001", "코드가 존재하지 않아요"),
    EMAIL_NOT_LOADED(HttpStatus.NOT_FOUND, "EMAIL-002", "이메일을 불러오지 못했어요"),
    EMAIL_NOT_MATCH(HttpStatus.BAD_REQUEST, "EMAIL-003", "이메일이 일치하지 않아요")

}