package seugi.server.domain.chat.application.service.message

import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import seugi.server.domain.chat.domain.chat.MessageEntity
import seugi.server.domain.chat.domain.chat.MessageRepository
import seugi.server.domain.chat.domain.chat.embeddable.Emoji
import seugi.server.domain.chat.domain.chat.mapper.MessageMapper
import seugi.server.domain.chat.domain.chat.model.Message
import seugi.server.domain.chat.domain.joined.JoinedRepository
import seugi.server.domain.chat.domain.enums.status.ChatStatusEnum
import seugi.server.domain.chat.exception.ChatErrorCode
import seugi.server.domain.chat.presentation.websocket.dto.ChatMessageDto
import seugi.server.domain.member.adapter.out.repository.MemberRepository
import seugi.server.global.exception.CustomException
import seugi.server.global.response.BaseResponse

@Service
class MessageServiceImpl(
    private val messageRepository: MessageRepository,
    private val memberRepository: MemberRepository,
    private val messageMapper: MessageMapper,
    private val joinedRepository: JoinedRepository
) : MessageService {

    override fun saveMessage(chatMessageDto: ChatMessageDto, userId: Long) : Message{
        val joinedEntity = joinedRepository.findByChatRoomId(chatMessageDto.roomId!!)

        val memberEntity = memberRepository.findById(userId)
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

    override fun getMessages(chatRoomId: Long, userId: Long) : BaseResponse<MutableMap<String, Any>> {

        if (joinedRepository.findByChatRoomId(chatRoomId).joinedUserId.contains(userId)){
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
                status = HttpStatus.OK,
                success = true,
                state = "M1",
                message = "채팅 불러오기 성공",
                data = data
            )

        } else throw CustomException(ChatErrorCode.NO_ACCESS_ROOM)

    }


    override fun addEmojiToMessage(userId: Long, messageId: String, emoji: Emoji): BaseResponse<Unit> {
        val id = ObjectId(messageId)
        val message: MessageEntity = messageRepository.findById(id).get()

        message.emojiList.firstOrNull { it.emojiId == emoji.emojiId }?.userId?.add(userId)

        messageRepository.save(message)

        return BaseResponse(
            status = HttpStatus.OK,
            state = "M1",
            success = true,
            message = "이모지 추가 성공"
        )
    }

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
            status = HttpStatus.OK,
            state = "M1",
            success = true,
            message = "메시지 지워짐으로 상태변경 성공"
        )
    }
}