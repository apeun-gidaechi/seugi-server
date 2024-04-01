package seugi.server.domain.chat.application.service.message

import org.springframework.stereotype.Service
import seugi.server.domain.chat.domain.chat.MessageRepository
import seugi.server.domain.chat.domain.chat.mapper.MessageMapper
import seugi.server.domain.chat.domain.joined.JoinedRepository
import seugi.server.domain.chat.presentation.websocket.dto.ChatMessageDto
import seugi.server.domain.member.adapter.out.repository.MemberRepository

@Service
class MessageServiceImpl(
    private val messageRepository: MessageRepository,
    private val memberRepository: MemberRepository,
    private val messageMapper: MessageMapper,
    private val joinedRepository: JoinedRepository
) : MessageService {

    override fun saveMessage(chatMessageDto: ChatMessageDto, userId: Long){

        val joinedEntity = joinedRepository.findById(chatMessageDto.roomId)
        messageRepository.save(
            messageMapper.toEntity(
                messageMapper.toMessage(
                    chatMessageDto = chatMessageDto,
                    joinedEntity = joinedEntity.get(),
                    userId = userId,
                    writer = memberRepository.findById(userId).get().name
                )
            )
        )
    }

}