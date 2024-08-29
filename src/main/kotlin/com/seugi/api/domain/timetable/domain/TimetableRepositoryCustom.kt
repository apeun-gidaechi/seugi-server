package com.seugi.api.domain.timetable.domain

interface TimetableRepositoryCustom {
    fun checkTimetableByWorkspaceId(workspaceId: String): Boolean
    fun findTodayTimetableByWorkspaceId(workspaceId: String, date: String): List<TimetableEntity>
    fun findWeekendTimetableByWorkspaceId(workspaceId: String): List<TimetableEntity>
}