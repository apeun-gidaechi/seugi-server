package com.seugi.api.domain.notification.domain

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository


@Repository
class NotificationRepositoryCustomImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : NotificationRepositoryCustom {

    override fun findByWorkspaceId(workspaceId: String, pageable: Pageable): List<NotificationEntity> {
        val notice: QNotificationEntity = QNotificationEntity.notificationEntity

        val noticeIdList = jpaQueryFactory
            .select(notice.id)
            .from(notice)
            .where(
                notice.workspaceId.eq(workspaceId),
                notice.deleted.isFalse
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(notice.id.desc())
            .fetch().orEmpty().toList()

        return jpaQueryFactory
            .select(notice)
            .from(notice)
            .leftJoin(notice.user).fetchJoin()
            .leftJoin(notice.emoji).fetchJoin()
            .where(notice.id.`in`(noticeIdList))
            .orderBy(notice.id.desc())
            .fetch().orEmpty().toList()

    }

}