package com.seugi.api.domain.chat.application.service.message

import com.seugi.api.domain.chat.domain.chat.MessageEntity
import com.seugi.api.domain.chat.domain.chat.MessageRepository
import com.seugi.api.domain.chat.domain.chat.embeddable.AddEmoji
import com.seugi.api.domain.chat.domain.chat.embeddable.DeleteMessage
import com.seugi.api.domain.chat.domain.chat.mapper.MessageMapper
import com.seugi.api.domain.chat.domain.chat.model.Message
import com.seugi.api.domain.chat.domain.chat.model.Type
import com.seugi.api.domain.chat.domain.enums.status.ChatStatusEnum
import com.seugi.api.domain.chat.domain.room.model.Room
import com.seugi.api.domain.chat.exception.ChatErrorCode
import com.seugi.api.domain.chat.presentation.chat.member.dto.response.GetMessageResponse
import com.seugi.api.domain.chat.presentation.message.dto.MessageResponse
import com.seugi.api.domain.chat.presentation.websocket.dto.ChatMessageDto
import com.seugi.api.domain.chat.presentation.websocket.dto.MessageEventDto
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.infra.fcm.FCMService
import com.seugi.api.global.response.BaseResponse
import org.bson.types.ObjectId
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class MessageServiceImpl(
    private val messageRepository: MessageRepository,
    private val messageMapper: MessageMapper,
    private val rabbitTemplate: RabbitTemplate,
    private val fcmService: FCMService,
) : MessageService {

    @Transactional
    override fun sendAndSaveMessage(chatMessageDto: ChatMessageDto, userId: Long) {
        rabbitTemplate.convertAndSend(
            "chat.exchange", "room.${chatMessageDto.roomId}", saveMessage(chatMessageDto, userId)
        )
    }

    override fun sendEventMessage(message: MessageEventDto, roomId: String) {
        rabbitTemplate.convertAndSend(
            "chat.exchange", "room.${roomId}", message
        )
    }

    private fun sendAlarm(message: Message, userId: Long) {
        fcmService.sendChatAlarm(
            message = message.message,
            chatRoomId = message.chatRoomId,
            userId = userId
        )
    }

    private fun saveMessage(chatMessageDto: ChatMessageDto, userId: Long): MessageResponse {

        val message = messageMapper.toDomain(
            messageRepository.save(
                messageMapper.toEntity(
                    messageMapper.toMessage(
                        chatMessageDto = chatMessageDto,
                        author = userId,
                    )
                )
            )
        )

        sendAlarm(message, userId)

        return messageMapper.toMessageResponse(message, chatMessageDto.uuid)

    }

    @Transactional(readOnly = true)
    override fun getMessage(roomId: String): MessageEntity? {
        return messageRepository.findByChatRoomId(roomId).lastOrNull()
    }


    @Transactional(readOnly = true)
    override fun getMessages(
        chatRoomId: String,
        userId: Long,
        timestamp: LocalDateTime,
    ): BaseResponse<GetMessageResponse> {

        val allMessages =
            messageRepository.findTop30ByChatRoomIdEqualsAndTimestampBefore(chatRoomId, timestamp)
                .map { messageMapper.toDomain(it) }

            return BaseResponse(
                message = "채팅 불러오기 성공",
                data = GetMessageResponse(
                    firstMessageId = if (allMessages.isEmpty()) null else allMessages.last().id,
                    messages = allMessages
                )
            )

    }

    @Transactional(readOnly = true)
    override fun getNotReadMessageCount(room: Room, userId: Long): Int {
        val timestamp = room.joinUserInfo.find { it.userId == userId }?.timestamp
        return messageRepository.findByChatRoomIdEqualsAndTimestampBefore(
            chatRoomId = room.id.toString(),
            timestamp = timestamp ?: LocalDateTime.now()
        ).count()
    }

    @Transactional
    override fun addEmojiToMessage(userId: Long, emoji: AddEmoji): BaseResponse<Unit> {
        val id = ObjectId(emoji.messageId)
        val message: MessageEntity = messageRepository.findById(id).get()

        message.emojiList.firstOrNull { it.emojiId == emoji.emojiId }?.userId?.add(userId)

        messageRepository.save(message)

        sendEventMessage(
            MessageEventDto(
                type = Type.ADD_EMOJI,
                userId = userId,
                messageId = emoji.messageId,
                emojiId = emoji.emojiId
            ),
            roomId = emoji.roomId!!
        )

        return BaseResponse(
            message = "이모지 추가 성공"
        )
    }

    @Transactional
    override fun deleteEmojiToMessage(userId: Long, emoji: AddEmoji): BaseResponse<Unit> {
        val id = ObjectId(emoji.messageId)
        val message: MessageEntity = messageRepository.findById(id).get()

        message.emojiList.firstOrNull { it.emojiId == emoji.emojiId }?.userId?.remove(userId)

        messageRepository.save(message)

        sendEventMessage(
            MessageEventDto(
                type = Type.REMOVE_EMOJI,
                userId = userId,
                messageId = emoji.messageId,
                emojiId = emoji.emojiId
            ),
            roomId = emoji.roomId!!
        )

        return BaseResponse(
            message = "이모지 삭제 성공"
        )
    }

    @Transactional
    override fun deleteMessage(userId: Long, deleteMessage: DeleteMessage): BaseResponse<Unit> {
        val id = ObjectId(deleteMessage.messageId)
        val message: MessageEntity = messageRepository.findById(id).get()

        if (message.userId != userId) throw CustomException(ChatErrorCode.NO_ACCESS_MESSAGE)

        message.messageStatus = ChatStatusEnum.DELETE
        messageRepository.save(message)

        sendEventMessage(
            MessageEventDto(
                type = Type.DELETE_MESSAGE,
                userId = userId,
                messageId = deleteMessage.messageId,
            ),
            deleteMessage.roomId!!
        )

        return BaseResponse(
            message = "메시지 지워짐으로 상태변경 성공"
        )
    }

}