package com.seugi.api.domain.notification.domain

import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import com.seugi.api.domain.notification.domain.embeddable.NotificationEmoji
import com.seugi.api.domain.notification.presentation.dto.request.UpdateNotificationRequest
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class NotificationEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val workspaceId: String,

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: MemberEntity? = null,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var content: String,

    @ElementCollection
    @Column(nullable = false)
    val emoji: MutableList<NotificationEmoji> = mutableListOf(),

    @Column(nullable = false, updatable = false)
    @CreatedDate
    var creationDate: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    @LastModifiedDate
    var lastModifiedDate: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var deleted: Boolean = false,
) {
    fun updateNotice(updateNoticeRequest: UpdateNotificationRequest) {
        this.title = updateNoticeRequest.title
        this.content = updateNoticeRequest.content
    }
    fun deleteNotice() {
        this.deleted = true
    }
}