package com.seugi.api.domain.chat.presentation.chat.room.dto.response

import com.seugi.api.domain.member.adapter.`in`.dto.res.RetrieveMemberResponse

data class UserInfoResponse(
    val userInfo: RetrieveMemberResponse,
    val timestamp: String,
)