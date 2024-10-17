package com.seugi.api.domain.ai.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.seugi.api.domain.ai.presentation.dto.AiResponse
import com.seugi.api.domain.chat.domain.chat.model.Message
import com.seugi.api.domain.chat.domain.chat.model.Type
import com.seugi.api.domain.chat.domain.room.ChatRoomEntity
import com.seugi.api.domain.chat.domain.room.ChatRoomRepository
import com.seugi.api.domain.chat.presentation.websocket.dto.ChatMessageDto
import com.seugi.api.domain.meal.service.MealService
import com.seugi.api.domain.notification.service.NotificationService
import com.seugi.api.domain.timetable.service.TimetableService
import com.seugi.api.domain.workspace.exception.WorkspaceErrorCode
import com.seugi.api.global.exception.CustomException
import org.bson.types.ObjectId
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.ai.openai.OpenAiChatOptions
import org.springframework.ai.openai.api.OpenAiApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AiService(
    private val openAiChatModel: OpenAiChatModel,
    private val timetableService: TimetableService,
    private val notificationService: NotificationService,
    private val mealService: MealService,
    private val chatRoomRepository: ChatRoomRepository,
    @Value("\${prompt.role}") private val rolePrompt: String,
    @Value("\${prompt.before}") private val beforePrompt: String,
    @Value("\${prompt.after}") private val afterPrompt: String,

    ) {

    private fun getToday(): String {
        return LocalDate.now().formatForTimetable()
    }

    private fun LocalDate.formatForTimetable(): String {
        return "$year-${monthValue.dateFormat()}-${dayOfMonth.dateFormat()}"
    }

    private fun Int.dateFormat(): String {
        return "%02d".format(this)
    }

    private fun findChatRoomById(id: String): ChatRoomEntity {
        return chatRoomRepository.findById(ObjectId(id)).orElseThrow { CustomException(WorkspaceErrorCode.NOT_FOUND) }
    }

    private fun Any.toJsonString(): String {
        val mapper = jacksonObjectMapper()
        return mapper.writeValueAsString(this)
    }

    private val requestModel = OpenAiChatOptions.builder().withModel(OpenAiApi.ChatModel.GPT_4_O_MINI).build()
    private val responseModel = OpenAiChatOptions.builder().withModel(OpenAiApi.ChatModel.GPT_4_TURBO).build()

    fun handleRequest(
        message: Message,
    ): ChatMessageDto {
        val promptTemplate = PromptTemplate(rolePrompt + beforePrompt)

        val prompt = promptTemplate.create(mapOf("message" to message.message), requestModel)

        val result = openAiChatModel.call(prompt).result.output.content
        return handleResponse(result, message)
    }

    private fun handleResponse(
        result: String,
        message: Message,
    ): ChatMessageDto {

        val date: Any = when (result) {
            "급식" -> {
                mealService.getMealByDate(
                    mealDate = getToday(),
                    workspaceId = findChatRoomById(message.chatRoomId).workspaceId
                )
            }

            "시간표" -> {
                timetableService.getDayTimetableByUserInfo(
                    workspaceId = findChatRoomById(message.chatRoomId).workspaceId,
                    userId = message.userId
                )
            }

            "공지" -> {
                notificationService.getNotices(
                    workspaceId = findChatRoomById(message.chatRoomId).workspaceId,
                    userId = message.userId,
                    pageable = PageRequest.of(0, 1)
                )
            }

            "사람 뽑기" -> {
                findChatRoomById(message.chatRoomId).joinedUserInfo.map {
                    it.userId
                }
            }

            "팀짜기" -> {
                findChatRoomById(message.chatRoomId).joinedUserInfo.map {
                    it.userId
                }
            }

            else -> {
                "기타의 경우엔 사용자의 응답에 맞춰 자유롭게 답변을 진행해주면 됩니다."
            }
        }


        return when (result) {
            "급식", "시간표", "공지" -> {
                ChatMessageDto(
                    type = Type.BOT,
                    roomId = message.chatRoomId,
                    message = AiResponse(
                        keyword = result,
                        data = date
                    ).toJsonString(),
                    mention = setOf(message.userId)
                )
            }

            else -> {
                val promptTemplate = PromptTemplate(rolePrompt + afterPrompt)
                println(date)
                val aiResult = openAiChatModel.call(
                    promptTemplate.create(
                        mapOf(
                            "first" to message.message,
                            "keyword" to result,
                            "data" to date.toJsonString()
                        ), responseModel
                    )
                ).result.output.content
                return ChatMessageDto(
                    type = Type.BOT,
                    roomId = message.chatRoomId,
                    message = AiResponse(
                        keyword = result,
                        data = aiResult
                    ).toJsonString(),
                    mention = setOf(message.userId)
                )
            }
        }
    }
}