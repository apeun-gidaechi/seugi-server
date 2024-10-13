package com.seugi.api.domain.schedule.presentation

import com.seugi.api.domain.schedule.domain.model.Schedule
import com.seugi.api.domain.schedule.service.ScheduleService
import com.seugi.api.global.common.annotation.GetAuthenticatedId
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/schedule")
class ScheduleController(
    private val scheduleService: ScheduleService,
) {

    @GetMapping("/{workspaceId}")
    fun getSchoolSchedules(
        @PathVariable workspaceId: String,
        @GetAuthenticatedId userId: Long,
    ): BaseResponse<List<Schedule>> {
        return scheduleService.getSchoolSchedules(userId, workspaceId)
    }

    @GetMapping("/month")
    fun getMonthSchedules(
        @RequestParam("workspaceId") workspaceId: String,
        @RequestParam("month") month: Int,
        @GetAuthenticatedId userId: Long,
    ): BaseResponse<List<Schedule>> {
        return scheduleService.getMonthSchoolSchedules(userId, workspaceId, month)
    }


}