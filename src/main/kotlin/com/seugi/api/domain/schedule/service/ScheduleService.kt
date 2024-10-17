package com.seugi.api.domain.schedule.service

import com.seugi.api.domain.schedule.domain.model.Schedule

interface ScheduleService {
    fun getSchoolSchedules(userId: Long, workspaceId: String): List<Schedule>
    fun getMonthSchoolSchedules(userId: Long, workspaceId: String, month: Int): List<Schedule>
}