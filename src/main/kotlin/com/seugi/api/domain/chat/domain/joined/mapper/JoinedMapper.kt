//package com.seugi.api.domain.chat.domain.member.mapper
//
//import com.seugi.api.domain.chat.domain.enums.type.RoomType
//import com.seugi.api.domain.chat.domain.member.model.Joined
//import com.seugi.api.global.common.Mapper
//import org.springframework.stereotype.Component
//
//@Component
//class JoinedMapper : Mapper<Joined, JoinedEntity> {
//    override fun toDomain(entity: JoinedEntity): Joined {
//        return Joined(
//            joinUserId = entity.joinedUserId,
//            roomType = entity.roomType,
//            roomAdmin = entity.roomAdmin,
//            chatRoomId = entity.chatRoomId!!,
//            workspaceId = entity.workspaceID
//        )
//    }
//
//    override fun toEntity(domain: Joined): JoinedEntity {
//        return JoinedEntity(
//            joinedUserId = domain.joinUserId,
//            roomType = domain.roomType,
//            roomAdmin = domain.roomAdmin,
//            chatRoomId = domain.chatRoomId,
//            workspaceID = domain.workspaceId
//        )
//    }
//
//    fun toEntity(
//        chatRoomId: Long,
//        joinedUserId: List<Long>,
//        type: RoomType,
//        roomAdmin: Long,
//        workspaceId: String
//    ): JoinedEntity {
//        return JoinedEntity(
//            joinedUserId = joinedUserId.toMutableSet(),
//            roomType = type,
//            roomAdmin = roomAdmin,
//            chatRoomId = chatRoomId,
//            workspaceID = workspaceId
//        )
//    }
//}