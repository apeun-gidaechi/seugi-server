package com.seugi.api.domain.timetable.domain

interface TimetableRepositoryCustom {
    fun checkTimetableByWorkspaceId(workspaceId: String): Boolean
    fun findByWorkspaceId(workspaceId: String): List<TimetableEntity>
}