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
        timetableService.resetTimetable(workspaceId, userId)
        return BaseResponse(
            message = "시간표 재설정 완료"
        )
    }

    @GetMapping("/weekend")
    fun getWeekendTimetable(
        @RequestParam("workspaceId") workspaceId: String,
        @GetAuthenticatedId userId: Long,
    ): BaseResponse<List<Timetable>> {
        return BaseResponse(
            message = "시간표 조회 성공",
            data = timetableService.getWeekendTimetableByUserInfo(workspaceId, userId)
        )
    }

    @GetMapping("/day")
    fun getDayTimetable(
        @RequestParam("workspaceId") workspaceId: String,
        @GetAuthenticatedId userId: Long,
    ): BaseResponse<List<Timetable>> {
        return BaseResponse(
            message = "시간표 조회 성공",
            data = timetableService.getDayTimetableByUserInfo(workspaceId, userId)
        )
    }

}