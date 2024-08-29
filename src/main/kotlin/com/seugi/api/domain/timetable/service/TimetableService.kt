package com.seugi.api.domain.timetable.service

import com.seugi.api.domain.timetable.domain.model.Timetable
import com.seugi.api.global.response.BaseResponse

interface TimetableService {
    fun resetTimetable(workspaceId: String, userId: Long): BaseResponse<Unit>
    fun getWeekendTimetableByUserInfo(workspaceId: String, userId: Long): BaseResponse<List<Timetable>>
    fun getDayTimetableByUserInfo(workspaceId: String, userId: Long): BaseResponse<List<Timetable>>
}