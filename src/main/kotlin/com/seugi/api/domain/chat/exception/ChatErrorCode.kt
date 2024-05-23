package com.seugi.api.domain.chat.exception

import com.seugi.api.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus

enum class ChatErrorCode(
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
) : CustomErrorCode {
    CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "채팅방을 찾을 수 없습니다."),
    NO_ACCESS_ROOM(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "채팅방 권한이 없습니다."),
    NO_ACCESS_MESSAGE(HttpStatus.FORBIDDEN, "FORBIDDEN", "메세지 삭제 권한이 없습니다."),
    CHAT_ROOM_CREATE_ERROR(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "채팅방 인원을 확인해주세요.")
}