package com.seugi.api.domain.schedule.service

import com.seugi.api.domain.schedule.domain.ScheduleRepository
import com.seugi.api.domain.schedule.domain.mapper.ScheduleMapper
import com.seugi.api.domain.schedule.domain.model.Schedule
import com.seugi.api.domain.schedule.exception.ScheduleException
import com.seugi.api.domain.workspace.domain.model.SchoolInfo
import com.seugi.api.domain.workspace.service.WorkspaceService
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.infra.nice.school.NiceSchoolService
import com.seugi.api.global.response.BaseResponse
import org.springframework.stereotype.Service

@Service
class ScheduleServiceImpl(
    private val scheduleMapper: ScheduleMapper,
    private val scheduleRepository: ScheduleRepository,
    private val niceSchoolService: NiceSchoolService,
    private val workspaceService: WorkspaceService,
) : ScheduleService {

    private fun getSchoolSchedules(schoolInfo: SchoolInfo, workspaceId: String): List<Schedule> {
        return scheduleMapper.toDomain(
            row = niceSchoolService.getSchoolSchedule(schoolInfo),
            workspaceId = workspaceId
        )
    }

    private fun getWorkspaceInfo(workspaceId: String, userId: Long): SchoolInfo {
        val workspaceEntity = workspaceService.findWorkspaceById(workspaceId)
        if (workspaceEntity.workspaceAdmin != userId &&
            !workspaceEntity.middleAdmin.contains(userId) &&
            !workspaceEntity.teacher.contains(userId) &&
            !workspaceEntity.student.contains(userId)
        ) {
            throw CustomException(ScheduleException.FORBIDDEN)
        }
        return workspaceEntity.schoolInfo
    }

    private fun resetTimetable(workspaceId: String, userId: Long): List<Schedule> {
        val schoolInfo = getWorkspaceInfo(workspaceId, userId)
        val schoolSchedules = getSchoolSchedules(schoolInfo, workspaceId)
        val scheduleEntities = schoolSchedules.map { scheduleMapper.toEntity(it) }
        return scheduleRepository.saveAll(scheduleEntities).map { scheduleMapper.toDomain(it) }
    }

    override fun getSchoolSchedules(userId: Long, workspaceId: String): BaseResponse<List<Schedule>> {
        return BaseResponse(
            message = "학사일정 전부 불러오기 성공",
            data = if (scheduleRepository.existsByWorkspaceId(workspaceId)) resetTimetable(workspaceId, userId)
            else scheduleRepository.findByWorkspaceId(workspaceId).map { scheduleMapper.toDomain(it) }
        )
    }

    override fun getMonthSchoolSchedules(userId: Long, workspaceId: String, month: Int): BaseResponse<List<Schedule>> {
        return BaseResponse(
            message = "학사일정 한달치 불러오기 성공",
            data = if (scheduleRepository.existsByWorkspaceId(workspaceId)) {
                resetTimetable(workspaceId, userId).filter { sc ->
                    sc.date?.split("-")?.get(1)?.toIntOrNull() == month
                }
            } else scheduleRepository.findByMonth(workspaceId, month).map { scheduleMapper.toDomain(it) }
        )
    }

}