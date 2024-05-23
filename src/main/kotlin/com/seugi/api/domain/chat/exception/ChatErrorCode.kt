package com.seugi.api.domain.chat.exception

import com.seugi.api.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus

enum class ChatErrorCode(
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
) : CustomErrorCode {
    CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "C2", "채팅방을 찾을 수 없습니다."),
    NO_ACCESS_ROOM(HttpStatus.BAD_REQUEST, "C3", "채팅방 권한이 없습니다."),
    NO_ACCESS_MESSAGE(HttpStatus.FORBIDDEN, "C4", "메세지 삭제 권한이 없습니다."),
}