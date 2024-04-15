package com.seugi.api.domain.chat.presentation.joined.dto.request

data class TossMasterRequest(
    val roomId: Long,
    val tossUserId: Long
)