package com.seugi.api.domain.schedule.domain

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class ScheduleRepositoryCustomImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : ScheduleRepositoryCustom {


    override fun existsByWorkspaceId(workspaceId: String): Boolean {
        val scheduleEntity = QScheduleEntity.scheduleEntity
        return jpaQueryFactory
            .selectFrom(scheduleEntity)
            .where(scheduleEntity.workspaceId.eq(workspaceId))
            .fetchFirst() != null
    }

    override fun findByWorkspaceId(workspaceId: String): List<ScheduleEntity> {
        val scheduleEntity = QScheduleEntity.scheduleEntity
        return jpaQueryFactory
            .selectFrom(scheduleEntity)
            .where(scheduleEntity.workspaceId.eq(workspaceId))
            .fetch() ?: emptyList()
    }

    override fun findByMonth(workspaceId: String, month: Int): List<ScheduleEntity> {
        val scheduleEntity = QScheduleEntity.scheduleEntity
        return jpaQueryFactory
            .selectFrom(scheduleEntity)
            .where(scheduleEntity.date.substring(6, 8).eq(month.toString()), scheduleEntity.workspaceId.eq(workspaceId))
            .fetch() ?: emptyList()
    }


}