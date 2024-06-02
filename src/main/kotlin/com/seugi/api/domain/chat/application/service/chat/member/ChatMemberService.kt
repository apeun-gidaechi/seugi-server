package com.seugi.api.domain.chat.application.service.chat.member

import com.seugi.api.domain.chat.domain.enums.type.EventType
import com.seugi.api.domain.chat.presentation.chat.member.dto.request.ChatMemberEventRequest
import com.seugi.api.global.response.BaseResponse

interface ChatMemberService {
    fun performCommonEventChangeTasks(
        userId: Long,
        chatMemberEventRequest: ChatMemberEventRequest,
        eventType: EventType
    ): BaseResponse<Unit>
}