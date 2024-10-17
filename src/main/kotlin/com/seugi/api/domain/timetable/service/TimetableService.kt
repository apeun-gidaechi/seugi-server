package com.seugi.api.domain.timetable.service

import com.seugi.api.domain.timetable.domain.model.Timetable

interface TimetableService {
    fun resetTimetable(workspaceId: String, userId: Long)
    fun getWeekendTimetableByUserInfo(workspaceId: String, userId: Long): List<Timetable>
    fun getDayTimetableByUserInfo(workspaceId: String, userId: Long): List<Timetable>
}