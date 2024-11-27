package com.seugi.api.domain.schedule.presentation

import com.seugi.api.domain.member.domain.model.Member
import com.seugi.api.domain.schedule.domain.model.Schedule
import com.seugi.api.domain.schedule.service.ScheduleService
import com.seugi.api.global.common.annotation.GetResolvedMember
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
        @GetResolvedMember model: Member,
    ): BaseResponse<List<Schedule>> {
        return BaseResponse(
            message = "학사일정 전부 불러오기 성공",
            data = scheduleService.getSchoolSchedules(model.id, workspaceId)
        )
    }

    @GetMapping("/month")
    fun getMonthSchedules(
        @RequestParam("workspaceId") workspaceId: String,
        @RequestParam("month") month: Int,
        @GetResolvedMember model: Member,
    ): BaseResponse<List<Schedule>> {
        return BaseResponse(
            message = "학사일정 한달치 불러오기 성공",
            data = scheduleService.getMonthSchoolSchedules(model.id, workspaceId, month)
        )
    }


}