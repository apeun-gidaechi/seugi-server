package com.seugi.api.domain.notification.domain

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository


@Repository
class NotificationRepositoryCustomImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : NotificationRepositoryCustom {

    override fun findByWorkspaceId(workspaceId: String): List<NotificationEntity> {
        val notice: QNotificationEntity = QNotificationEntity.notificationEntity

        return jpaQueryFactory
            .select(notice)
            .from(notice)
            .where(
                notice.workspaceId.eq(workspaceId),
                notice.deleted.isFalse
            )
            .fetch().orEmpty().toList()

    }
}