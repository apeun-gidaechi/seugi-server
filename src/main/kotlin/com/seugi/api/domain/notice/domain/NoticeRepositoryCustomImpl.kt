package com.seugi.api.domain.notice.domain

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository


@Repository
class NoticeRepositoryCustomImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : NoticeRepositoryCustom {

    override fun findByWorkspaceId(workspaceId: String): List<NoticeEntity> {
        val notice: QNoticeEntity = QNoticeEntity.noticeEntity

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