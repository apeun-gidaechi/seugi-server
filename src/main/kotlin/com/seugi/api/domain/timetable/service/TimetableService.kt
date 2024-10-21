package com.seugi.api.domain.timetable.service

import com.seugi.api.domain.timetable.domain.model.Timetable
import com.seugi.api.domain.timetable.presentation.dto.request.CreateTimetableRequest
import com.seugi.api.domain.timetable.presentation.dto.request.FixTimetableRequest
import com.seugi.api.global.response.BaseResponse

interface TimetableService {
    fun createTimetable(createTimetableRequest: CreateTimetableRequest, userId: Long): BaseResponse<Unit>
    fun fixTimetable(userId: Long, fixTimetableRequest: FixTimetableRequest): BaseResponse<Unit>
    fun deleteTimetable(userId: Long, id: Long): BaseResponse<Unit>
    fun resetTimetable(workspaceId: String, userId: Long)
    fun getWeekendTimetableByUserInfo(workspaceId: String, userId: Long): List<Timetable>
    fun getDayTimetableByUserInfo(workspaceId: String, userId: Long): List<Timetable>
}