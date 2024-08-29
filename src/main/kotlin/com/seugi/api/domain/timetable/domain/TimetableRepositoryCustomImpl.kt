package com.seugi.api.domain.timetable.domain

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class TimetableRepositoryCustomImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : TimetableRepositoryCustom {

    override fun checkTimetableByWorkspaceId(workspaceId: String): Boolean {
        val timetableEntity = QTimetableEntity.timetableEntity

        return jpaQueryFactory
            .selectFrom(timetableEntity)
            .where(timetableEntity.workspaceId.eq(workspaceId))
            .fetchFirst() != null
    }

    override fun findTodayTimetableByWorkspaceId(workspaceId: String, date: String): List<TimetableEntity> {
        val timetableEntity = QTimetableEntity.timetableEntity

        return jpaQueryFactory
            .selectFrom(timetableEntity)
            .where(timetableEntity.workspaceId.eq(workspaceId), timetableEntity.date.eq(date))
            .fetch() ?: emptyList()
    }

    override fun findWeekendTimetableByWorkspaceId(workspaceId: String): List<TimetableEntity> {
        val timetableEntity = QTimetableEntity.timetableEntity

        return jpaQueryFactory
            .selectFrom(timetableEntity)
            .where(timetableEntity.workspaceId.eq(workspaceId))
            .fetch() ?: emptyList()
    }

    override fun deleteAllByWorkspaceId(workspaceId: String) {
        val timetableEntity = QTimetableEntity.timetableEntity

        jpaQueryFactory
            .delete(timetableEntity)
            .where(timetableEntity.workspaceId.eq(workspaceId))
            .execute()
    }

}