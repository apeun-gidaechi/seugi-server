package com.seugi.api.domain.chat.domain.room

import org.springframework.data.repository.CrudRepository

interface ChatRoomRepository : CrudRepository <ChatRoomEntity, Long> , ChatRoomRepositoryCustom
