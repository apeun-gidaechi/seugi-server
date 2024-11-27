package com.seugi.api.domain.ai.presentation

import com.seugi.api.domain.ai.service.AiService
import com.seugi.api.domain.chat.presentation.websocket.dto.ChatMessageDto
import com.seugi.api.domain.member.domain.model.Member
import com.seugi.api.global.common.annotation.GetResolvedMember
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ai")
class AiController(
    private val aiService: AiService,
) {

    @PostMapping
    fun generatedAiChatbot(
        @RequestBody chatMessageDto: ChatMessageDto,
        @GetResolvedMember model: Member,
    ): BaseResponse<String> {
        return BaseResponse(
            message = "캣스기답변",
            data = aiService.handleRequest(chatMessageDto, model.id).message
        )
    }

}