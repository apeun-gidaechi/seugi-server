package com.seugi.api.domain.timetable.domain

import org.springframework.data.repository.CrudRepository

interface TimetableRepository : CrudRepository<TimetableEntity, Long>, TimetableRepositoryCustom {
    override fun checkTimetableByWorkspaceId(workspaceId: String): Boolean
    override fun findTodayTimetableByWorkspaceId(workspaceId: String, date: String): List<TimetableEntity>
}