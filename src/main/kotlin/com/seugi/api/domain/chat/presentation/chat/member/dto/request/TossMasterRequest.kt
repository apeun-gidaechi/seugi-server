package com.seugi.api.domain.chat.presentation.chat.member.dto.request

data class TossMasterRequest(
    val roomId: Long,
    val tossUserId: Long
)