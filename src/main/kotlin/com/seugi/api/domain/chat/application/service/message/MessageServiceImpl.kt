package com.seugi.api.domain.chat.application.service.message

import com.seugi.api.domain.chat.domain.chat.MessageEntity
import com.seugi.api.domain.chat.domain.chat.MessageRepository
import com.seugi.api.domain.chat.domain.chat.embeddable.AddEmoji
import com.seugi.api.domain.chat.domain.chat.embeddable.DeleteMessage
import com.seugi.api.domain.chat.domain.chat.mapper.MessageMapper
import com.seugi.api.domain.chat.domain.chat.model.Message
import com.seugi.api.domain.chat.domain.chat.model.Type
import com.seugi.api.domain.chat.domain.enums.status.ChatStatusEnum
import com.seugi.api.domain.chat.domain.room.info.RoomInfoEntity
import com.seugi.api.domain.chat.domain.room.info.RoomInfoRepository
import com.seugi.api.domain.chat.exception.ChatErrorCode
import com.seugi.api.domain.chat.presentation.chat.member.dto.response.GetMessageResponse
import com.seugi.api.domain.chat.presentation.websocket.dto.ChatMessageDto
import com.seugi.api.domain.chat.presentation.websocket.dto.MessageEventDto
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.response.BaseResponse
import org.bson.types.ObjectId
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MessageServiceImpl(
    private val messageRepository: MessageRepository,
    private val messageMapper: MessageMapper,
    private val roomInfoRepository: RoomInfoRepository,
    private val rabbitTemplate: RabbitTemplate
) : MessageService {

    @Transactional
    override fun sendAndSaveMessage(chatMessageDto: ChatMessageDto, userId: Long) {
        rabbitTemplate.convertAndSend(
            "chat.exchange", "room.${chatMessageDto.roomId}", saveMessage(chatMessageDto, userId)
        )
    }

    @Transactional
    override fun sendEventMessage(message: MessageEventDto, roomId: String) {
        rabbitTemplate.convertAndSend(
            "chat.exchange", "room.${roomId}", message
        )
    }

    @Transactional
    override fun saveMessage(chatMessageDto: ChatMessageDto, userId: Long): Message {

        val readUser = roomInfoRepository.findByRoomId(chatMessageDto.roomId.toString()).orEmpty()
        val readUsers = readUser.map { it.userId }

        return messageMapper.toDomain(
            messageRepository.save(
                messageMapper.toEntity(
                    messageMapper.toMessage(
                        chatMessageDto = chatMessageDto,
                        author = userId,
                        readUsers = readUsers
                    )
                )
            )
        )

    }

    @Transactional(readOnly = true)
    override fun getMessage(roomId: String): MessageEntity? {
        return messageRepository.findByChatRoomId(roomId).lastOrNull()
    }


    @Transactional(readOnly = true)
    override fun getMessages(chatRoomId: String, userId: Long, pageable: Pageable): BaseResponse<GetMessageResponse> {

        val allMessages =
            messageRepository.findByChatRoomIdEquals(chatRoomId, pageable).map { messageMapper.toDomain(it) }


        val unreadMessages: List<MessageEntity> =
            messageRepository.findByChatRoomIdEqualsAndReadNotContains(chatRoomId, userId)

        if (unreadMessages.isNotEmpty()) {
            unreadMessages.map { it.read.add(userId) }

            messageRepository.saveAll(unreadMessages).last()

            return BaseResponse(
                message = "채팅 불러오기 성공",
                data = GetMessageResponse(
                    firstMessageId = unreadMessages.first().id?.toString(),
                    messages = allMessages
                )
            )
        } else {
            return BaseResponse(
                message = "채팅 불러오기 성공",
                data = GetMessageResponse(
                    firstMessageId = if (allMessages.isEmpty()) null else allMessages.last().id,
                    messages = allMessages
                )
            )
        }
    }

    @Transactional(readOnly = true)
    override fun getNotReadMessageCount(chatRoomId: String, userId: Long): Int {
        return messageRepository.findByChatRoomIdEqualsAndReadNotContains(chatRoomId, userId).count()
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

    @Transactional
    override fun sub(userId: Long, roomId: String) {
        if (roomId != "message" && roomId.length == 24) {

            sendEventMessage(
                message = MessageEventDto(
                    userId = userId,
                    type = Type.SUB
                ),
                roomId = roomId
            )
            roomInfoRepository.save(
                RoomInfoEntity(
                    userId = userId,
                    roomId = roomId
                )
            )
        }
    }

    @Transactional
    override fun unSub(userId: Long) {
        roomInfoRepository.deleteById(userId)
    }

}