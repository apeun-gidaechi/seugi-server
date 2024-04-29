package com.seugi.api.domain.chat.application.service.message

import com.seugi.api.domain.chat.application.service.joined.JoinedService
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.seugi.api.domain.chat.domain.chat.MessageEntity
import com.seugi.api.domain.chat.domain.chat.MessageRepository
import com.seugi.api.domain.chat.domain.chat.embeddable.Emoji
import com.seugi.api.domain.chat.domain.chat.mapper.MessageMapper
import com.seugi.api.domain.chat.domain.chat.model.Message
import com.seugi.api.domain.chat.domain.enums.status.ChatStatusEnum
import com.seugi.api.domain.chat.exception.ChatErrorCode
import com.seugi.api.domain.chat.presentation.websocket.dto.ChatMessageDto
import com.seugi.api.domain.member.adapter.out.repository.MemberRepository
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.response.BaseResponse
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.messaging.simp.SimpAttributesContextHolder

@Service
class MessageServiceImpl(
    private val messageRepository: MessageRepository,
    private val memberRepository: MemberRepository,
    private val messageMapper: MessageMapper,
    private val joinedService: JoinedService,
    private val rabbitTemplate: RabbitTemplate
) : MessageService {

    @Transactional
    override fun message(chatMessageDto: ChatMessageDto){
        rabbitTemplate.convertAndSend(
            "chat.exchange", "room.${chatMessageDto.roomId}", savaMessage(chatMessageDto)
        )
    }

    @Transactional
    override fun savaMessage(chatMessageDto: ChatMessageDto) : Message {
        val simpAttributes = SimpAttributesContextHolder.currentAttributes()
        val userId = simpAttributes.getAttribute("user-id") as String?

        val joinedEntity = joinedService.findByRoomId(chatMessageDto.roomId!!)

        val memberEntity = memberRepository.findById(userId!!.toLong())
            .orElseThrow { CustomException(ChatErrorCode.CHAT_ROOM_NOT_FOUND) }

        return messageMapper.toDomain(
            messageRepository.save(
                messageMapper.toEntity(
                    messageMapper.toMessage(
                        chatMessageDto = chatMessageDto,
                        author = memberEntity,
                        joinedEntity = joinedEntity,
                    )
                )
            )
        )

    }

    @Transactional(readOnly = true)
    override fun getMessages(chatRoomId: Long, userId: Long) : BaseResponse<MutableMap<String, Any>> {

        if (!joinedService.findByRoomId(chatRoomId).joinedUserId.contains(userId)) throw CustomException(ChatErrorCode.NO_ACCESS_ROOM)

        val read : Set<Long> = setOf(userId)
        val messages : List<MessageEntity> = messageRepository.findByChatRoomIdEqualsAndReadNot(chatRoomId, read)

        val data : MutableMap<String, Any> = emptyMap<String, List<Message>>().toMutableMap()

        if (messages.isNotEmpty()){
            messages.map {
                it.read.add(userId)
            }
            val id = messageRepository.saveAll(messages).last()
            data["firstMessageId"] = messages.first().id?:id
            data["messages"] = messageRepository.findByChatRoomIdEquals(chatRoomId).map { messageMapper.toDomain(it) }
        } else {
            val readMessages = messageRepository.findByChatRoomIdEquals(chatRoomId).map { messageMapper.toDomain(it) }
            data["firstMessageId"] = readMessages.last().id!!
            data["messages"] = readMessages
        }


        return BaseResponse(
            status = HttpStatus.OK.value(),
            success = true,
            state = "M1",
            message = "채팅 불러오기 성공",
            data = data
        )


    }

    @Transactional
    override fun addEmojiToMessage(userId: Long, messageId: String, emoji: Emoji): BaseResponse<Unit> {
        val id = ObjectId(messageId)
        val message: MessageEntity = messageRepository.findById(id).get()

        message.emojiList.firstOrNull { it.emojiId == emoji.emojiId }?.userId?.add(userId)

        messageRepository.save(message)

        return BaseResponse(
            status = HttpStatus.OK.value(),
            state = "M1",
            success = true,
            message = "이모지 추가 성공"
        )
    }

    @Transactional
    override fun deleteMessage(userId: Long, messageId: String): BaseResponse<Unit> {
        val id = ObjectId(messageId)
        val message: MessageEntity = messageRepository.findById(id).get()

        if (message.author.id == userId) {
            message.messageStatus = ChatStatusEnum.DELETE
            messageRepository.save(message)
        } else {
            throw CustomException(ChatErrorCode.NO_ACCESS_MESSAGE)
        }

        return BaseResponse(
            status = HttpStatus.OK.value(),
            state = "M1",
            success = true,
            message = "메시지 지워짐으로 상태변경 성공"
        )
    }
}