package com.seugi.api.domain.ai.presentation

import com.seugi.api.domain.ai.service.AiService
import com.seugi.api.domain.chat.presentation.websocket.dto.ChatMessageDto
import com.seugi.api.global.common.annotation.GetAuthenticatedId
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.GetMapping
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
        @GetAuthenticatedId userId: Long,
    ): BaseResponse<String> {
        return BaseResponse(
            message = "캣스기답변",
            data = aiService.handleRequest(chatMessageDto, userId).message
        )
    }

}