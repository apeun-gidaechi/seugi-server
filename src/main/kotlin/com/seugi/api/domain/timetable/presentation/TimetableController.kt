package com.seugi.api.domain.timetable.presentation

import com.seugi.api.domain.member.domain.model.Member
import com.seugi.api.domain.timetable.domain.model.Timetable
import com.seugi.api.domain.timetable.presentation.dto.request.CreateTimetableRequest
import com.seugi.api.domain.timetable.presentation.dto.request.FixTimetableRequest
import com.seugi.api.domain.timetable.service.TimetableService
import com.seugi.api.global.common.annotation.GetResolvedMember
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/timetable")
class TimetableController(
    private val timetableService: TimetableService,
) {

    @PostMapping(path = ["", "/"])
    fun createTimetable(
        @GetResolvedMember model: Member,
        @RequestBody timetable: CreateTimetableRequest,
    ): BaseResponse<Unit> {
        return timetableService.createTimetable(timetable, model.id)
    }

    @PatchMapping(path = ["", "/"])
    fun fixTimetable(
        @GetResolvedMember model: Member,
        @RequestBody fixTimetableRequest: FixTimetableRequest,
    ): BaseResponse<Unit> {
        return timetableService.fixTimetable(model.id, fixTimetableRequest)
    }

    @DeleteMapping("/{id}")
    fun deleteTimetable(
        @GetResolvedMember model: Member,
        @PathVariable id: Long,
    ): BaseResponse<Unit> {
        return timetableService.deleteTimetable(model.id, id)
    }


    @PostMapping("/reset")
    fun resetTimetable(
        @RequestParam("workspaceId") workspaceId: String,
        @GetResolvedMember model: Member,
    ): BaseResponse<Unit> {
        timetableService.resetTimetable(workspaceId, model.id)
        return BaseResponse(
            message = "시간표 재설정 완료"
        )
    }

    @GetMapping("/weekend")
    fun getWeekendTimetable(
        @RequestParam("workspaceId") workspaceId: String,
        @GetResolvedMember model: Member,
    ): BaseResponse<List<Timetable>> {
        return BaseResponse(
            message = "시간표 조회 성공",
            data = timetableService.getWeekendTimetableByUserInfo(workspaceId, model.id)
        )
    }

    @GetMapping("/day")
    fun getDayTimetable(
        @RequestParam("workspaceId") workspaceId: String,
        @GetResolvedMember model: Member,
    ): BaseResponse<List<Timetable>> {
        return BaseResponse(
            message = "시간표 조회 성공",
            data = timetableService.getDayTimetableByUserInfo(workspaceId, model.id)
        )
    }

}