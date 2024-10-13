package com.seugi.api.domain.schedule.service

import com.seugi.api.domain.schedule.domain.model.Schedule
import com.seugi.api.global.response.BaseResponse

interface ScheduleService {
    fun getSchoolSchedules(userId: Long, workspaceId: String): BaseResponse<List<Schedule>>
    fun getMonthSchoolSchedules(userId: Long, workspaceId: String, month: Int): BaseResponse<List<Schedule>>
}