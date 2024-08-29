package com.seugi.api.domain.timetable.presentation

import com.seugi.api.domain.timetable.domain.model.Timetable
import com.seugi.api.domain.timetable.service.TimetableService
import com.seugi.api.global.common.annotation.GetAuthenticatedId
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/timetable")
class TimetableController(
    private val timetableService: TimetableService,
) {

    @PostMapping("/reset")
    fun resetTimetable(
        @RequestParam("workspaceId") workspaceId: String,
        @GetAuthenticatedId userId: Long,
    ): BaseResponse<Unit> {
        return timetableService.resetTimetable(workspaceId, userId)
    }

    @GetMapping("/weekend")
    fun getWeekendTimetable(
        @RequestParam("workspaceId") workspaceId: String,
        @GetAuthenticatedId userId: Long,
    ): BaseResponse<List<Timetable>> {
        return timetableService.getWeekendTimetableByUserInfo(workspaceId, userId)
    }

    @GetMapping("/day")
    fun getDayTimetable(
        @RequestParam("workspaceId") workspaceId: String,
        @GetAuthenticatedId userId: Long,
    ): BaseResponse<List<Timetable>> {
        return timetableService.getDayTimetableByUserInfo(workspaceId, userId)
    }

}