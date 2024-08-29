package com.seugi.api.domain.timetable.service

import com.seugi.api.domain.timetable.domain.model.Timetable
import com.seugi.api.global.response.BaseResponse

interface TimetableService {
    fun getWeekendTimetableByUserInfo(workspaceId: String, userId: Long): BaseResponse<Timetable>
}