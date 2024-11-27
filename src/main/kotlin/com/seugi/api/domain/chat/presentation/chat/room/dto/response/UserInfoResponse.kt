package com.seugi.api.domain.chat.presentation.chat.room.dto.response

import com.seugi.api.domain.member.presentation.controller.dto.res.RetrieveMemberResponse

data class UserInfoResponse(
    val userInfo: RetrieveMemberResponse,
    val timestamp: String,
)