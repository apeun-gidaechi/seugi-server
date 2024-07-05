package com.seugi.api.domain.notice.domain

import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class NoticeEntity(

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
    val emoji: List<String> = emptyList(),

    @Column(nullable = false, updatable = false)
    @CreatedDate
    var creationDate: LocalDateTime? = null,

    @Column(nullable = false)
    @LastModifiedDate
    var lastModifiedDate: LocalDateTime? = null,

    @Column(nullable = false)
    var deleted: Boolean = false,
)