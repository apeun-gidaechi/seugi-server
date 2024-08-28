package com.seugi.api.domain.timetable.domain

import com.querydsl.jpa.impl.JPAQueryFactory

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

    override fun findByWorkspaceId(workspaceId: String): List<TimetableEntity?> {
        val timetableEntity = QTimetableEntity.timetableEntity

        return jpaQueryFactory
            .selectFrom(timetableEntity)
            .where(timetableEntity.workspaceId.eq(workspaceId))
            .fetch()
    }

}