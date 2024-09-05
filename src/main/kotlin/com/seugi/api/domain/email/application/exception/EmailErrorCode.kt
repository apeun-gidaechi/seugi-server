package com.seugi.api.domain.email.application.exception

import org.springframework.http.HttpStatus
import com.seugi.api.global.exception.CustomErrorCode

enum class EmailErrorCode (

    override val status: HttpStatus,
    override val state: String,
    override val message: String,

) : CustomErrorCode {

    EMAIL_NOT_LOADED(HttpStatus.NOT_FOUND, "NOT_FOUND", "이메일을 불러오지 못했어요"),
    CODE_NOT_MATCH(HttpStatus.CONFLICT, "CONFLICT", "코드가 일치하지 않아요"),

}