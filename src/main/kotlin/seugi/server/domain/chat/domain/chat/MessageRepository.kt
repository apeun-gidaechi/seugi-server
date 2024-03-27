package seugi.server.domain.chat.domain.chat

import org.springframework.data.repository.CrudRepository

interface MessageRepository :CrudRepository<MessageEntity, Long>