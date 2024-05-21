package com.seugi.api.domain.profile.application.exception

import com.seugi.api.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus

enum class ProfileErrorCode (
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
) : CustomErrorCode {

    PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "PROFILE-001", "프로필을 찾을 수 없음")

}