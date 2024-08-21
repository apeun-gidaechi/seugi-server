package com.seugi.api.domain.timetable.service

interface TimetableService {
    fun getWeekendTimetable(workspaceId: String, userId: Long)
}