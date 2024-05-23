package com.seugi.api.domain.chat.domain.room

import org.springframework.data.mongodb.repository.MongoRepository

interface ChatRoomRepository : MongoRepository<ChatRoomEntity, Long>