package seugi.server.domain.chat.application.service.message

import org.springframework.stereotype.Service
import seugi.server.domain.chat.domain.chat.MessageRepository

@Service
class MessageServiceImpl(
    private val messageRepository: MessageRepository
) : MessageService {

}