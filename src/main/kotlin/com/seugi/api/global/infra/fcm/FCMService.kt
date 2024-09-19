package com.seugi.api.global.infra.fcm

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import com.seugi.api.domain.member.application.model.Member
import com.seugi.api.domain.member.application.port.out.LoadMemberPort
import com.seugi.api.domain.workspace.service.WorkspaceService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class FCMService(
    private val memberPort: LoadMemberPort,
    private val workspaceService: WorkspaceService,
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
            FCMEnums.PERSONAL -> member.getImg()
            FCMEnums.GROUP -> member.getImg()
            FCMEnums.NOTIFICATION -> workspaceService.findWorkspaceById(workspaceId).workspaceImageUrl ?: icon
        }
    }

    fun sendAlarm(body: String, workspaceId: String, type: FCMEnums, userId: Long) {

        val member = getMember(userId)

        val notification = Notification.builder()
            .setTitle(
                when (type) {
                    FCMEnums.PERSONAL -> FCMEnums.PERSONAL.type
                    FCMEnums.GROUP -> FCMEnums.GROUP.type
                    FCMEnums.NOTIFICATION -> FCMEnums.NOTIFICATION.type
                }
            )
            .setBody("${member.name.value} : $body")
            .setImage(getAlarmImage(workspaceId, type, member))
            .build()

        member.getFCMToken().map {
            FirebaseMessaging.getInstance().sendAsync(
                Message.builder()
                    .setToken(it)
                    .setNotification(notification)
                    .build()
            )
        }

    }

}