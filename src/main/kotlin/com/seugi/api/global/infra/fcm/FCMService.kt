package com.seugi.api.global.infra.fcm

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import com.seugi.api.domain.chat.application.service.chat.room.ChatRoomService
import com.seugi.api.domain.member.application.model.Member
import com.seugi.api.domain.member.application.port.out.LoadMemberPort
import com.seugi.api.domain.workspace.service.WorkspaceService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class FCMService(
    private val memberPort: LoadMemberPort,
    private val workspaceService: WorkspaceService,
    private val chatRoomService: ChatRoomService,
    @Value("\${fcm.icon.url}") private val icon: String,
) {

    private fun getMember(userId: Long): Member {
        return memberPort.loadMemberWithId(userId)
    }

    private fun Member.getImg(): String {
        return this.picture.value
    }

    private fun Member.getFCMToken(): Set<String> {
        return this.fcmToken.token
    }

    private fun getAlarmImage(workspaceId: String, type: FCMEnums, member: Member): String {
        return when (type) {
            FCMEnums.CHAT -> member.getImg()
            FCMEnums.NOTIFICATION -> workspaceService.findWorkspaceById(workspaceId).workspaceImageUrl ?: icon
        }
    }

    fun sendChatAlarm(message: String, chatRoomId: String, readUser: List<Long>, userId: Long) {

        val room = chatRoomService.findRoomById(chatRoomId)

        val unReadUsers = room.joinedUserId - readUser.toSet()

        val sendMember = getMember(userId)

        val notification = Notification.builder()
            .setTitle(
                room.chatName
            )
            .setBody("${sendMember.name.value} : $message")
            .setImage(
                getAlarmImage(
                    workspaceId = "",
                    type = FCMEnums.CHAT,
                    member = sendMember
                )
            )
            .build()

        unReadUsers.map { it ->
            getMember(it).getFCMToken().map {
                FirebaseMessaging.getInstance().sendAsync(
                    Message.builder()
                        .setToken(it)
                        .setNotification(notification)
                        .build()
                )
            }
        }

    }

    fun sendAlert() {

    }

}