package seugi.server.domain.chat.exception

import org.springframework.http.HttpStatus
import seugi.server.global.exception.CustomErrorCode

enum class ChatErrorCode (
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
): CustomErrorCode {

    CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "C2", "채팅방을 찾을 수 없습니다.")

}