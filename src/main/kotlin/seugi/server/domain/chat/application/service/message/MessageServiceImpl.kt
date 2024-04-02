package seugi.server.domain.chat.application.service.message

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import seugi.server.domain.chat.domain.chat.MessageEntity
import seugi.server.domain.chat.domain.chat.MessageRepository
import seugi.server.domain.chat.domain.chat.embeddable.Emoji
import seugi.server.domain.chat.domain.chat.mapper.MessageMapper
import seugi.server.domain.chat.domain.chat.model.Message
import seugi.server.domain.chat.domain.joined.JoinedRepository
import seugi.server.domain.chat.domain.status.ChatStatusEnum
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

    override fun saveMessage(chatMessageDto: ChatMessageDto){
        val joinedEntity = joinedRepository.findByChatRoomId(chatMessageDto.roomId!!)

        val memberEntity = memberRepository.findById(chatMessageDto.userId!!)
            .orElseThrow { IllegalArgumentException("해당 id로 MemberEntity를 찾을 수 없습니다.") }

        messageRepository.save(
            messageMapper.toEntity(
                messageMapper.toMessage(
                    chatMessageDto = chatMessageDto,
                    joinedEntity = joinedEntity,
                    userId = chatMessageDto.userId!!,
                    writer = memberEntity.name
                )
            )
        )
    }

    override fun getMessages(chatRoomId: Long, userId: Long) : BaseResponse<List<Message>> {

        if (joinedRepository.findByChatRoomId(chatRoomId).joinedUserId.contains(userId)){

            return BaseResponse(
                status = HttpStatus.OK,
                success = true,
                state = "M1",
                message = "채팅 불러오기 성공",
                data = messageRepository.findByChatRoomIdEquals(chatRoomId).map { messageMapper.toDomain(it) }
            )

        } else throw CustomException(ChatErrorCode.NO_ACCESS_ROOM)

    }

    override fun readMessage(userId: Long, chatRoomId: Long): BaseResponse<Unit> {
        val message: List<MessageEntity> = messageRepository.findByChatRoomIdAndUserIdEquals(userId, chatRoomId)

        message.map { it ->
            it.read.add(userId)
            it.unRead =  it.unRead.filterNot { it == userId }.toMutableList()
            messageRepository.save(it)
        }

        return BaseResponse(
            status = HttpStatus.OK,
            state = "M1",
            success = true,
            message = "읽음처리 성공"
        )
    }

    override fun addEmojiToMessage(userId: Long, messageId: Long, emoji: Emoji): BaseResponse<Unit> {

        val message: MessageEntity = messageRepository.findById(messageId).get()

        message.emoji.add(
            Emoji(
                userId = userId,
                emojiId = emoji.emojiId
            )
        )

        messageRepository.save(message)

        return BaseResponse(
            status = HttpStatus.OK,
            state = "M1",
            success = true,
            message = "이모지 추가 성공"
        )
    }

    override fun deleteMessage(userId: Long, messageId: Long): BaseResponse<Unit> {
        TODO("Not yet implemented")
    }
}