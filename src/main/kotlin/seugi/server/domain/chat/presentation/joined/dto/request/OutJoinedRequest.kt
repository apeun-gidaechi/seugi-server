package seugi.server.domain.chat.presentation.joined.dto.request

data class OutJoinedRequest(
    val roomId: Long? = null,
    val outJoinedUsers: List<Long> = emptyList()
)
