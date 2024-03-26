package seugi.server.domain.chat.domain.room

import org.springframework.data.repository.CrudRepository

interface ChatRoomRepository : CrudRepository <ChatRoomEntity, Long>
