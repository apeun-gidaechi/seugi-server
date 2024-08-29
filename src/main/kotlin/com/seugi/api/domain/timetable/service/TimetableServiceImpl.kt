package com.seugi.api.domain.timetable.service

import com.seugi.api.domain.member.application.port.out.LoadMemberPort
import com.seugi.api.domain.timetable.domain.TimetableRepository
import com.seugi.api.domain.timetable.domain.mapper.TimetableMapper
import com.seugi.api.domain.timetable.domain.model.Timetable
import com.seugi.api.domain.workspace.domain.model.SchoolInfo
import com.seugi.api.domain.workspace.service.WorkspaceService
import com.seugi.api.global.infra.nice.school.NiceSchoolService
import com.seugi.api.global.response.BaseResponse
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@Service
class TimetableServiceImpl(
    private val workspaceService: WorkspaceService,
    private val memberPort: LoadMemberPort,
    private val timetableMapper: TimetableMapper,
    private val timetableRepository: TimetableRepository,
    private val niceSchoolService: NiceSchoolService,
) : TimetableService {

    private fun resetTimetable(
        workspaceId: String,
    ) {
        val schoolInfo = workspaceService.findWorkspaceById(workspaceId).schoolInfo

        val date = getDate()

        val data = getSchoolTimetable(
            schoolInfo = schoolInfo,
            date = date,
            workspaceId = workspaceId
        )

        if (data.isNotEmpty()) {
            saveTimetable(data)
        } else {
            saveTimetable(
                listOf(Timetable(workspaceId = workspaceId))
            )
        }
    }

    private fun saveTimetable(timetables: List<Timetable>) {
        timetableRepository.saveAll(
            timetables.map {
                timetableMapper.toEntity(it)
            }.filter { it.classNum.isNotEmpty() }
        )
    }

    private fun getDate(): Pair<String, String> {
        val date = LocalDate.now()

        val startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val endOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))

        val startDate =
            "${startOfWeek.year}${"%02d".format(startOfWeek.month.value)}${"%02d".format(startOfWeek.dayOfMonth)}"
        val endDate = "${endOfWeek.year}${"%02d".format(endOfWeek.month.value)}${"%02d".format(endOfWeek.dayOfMonth)}"

        return Pair(startDate, endDate)
    }

    private fun getSchoolTimetable(
        schoolInfo: SchoolInfo,
        date: Pair<String, String>,
        workspaceId: String,
    ): List<Timetable> {
        return niceSchoolService.getSchoolTimeTable(
            schoolInfo = schoolInfo,
            startDate = date.first,
            endDate = date.second,
            workspaceId = workspaceId
        )!!.map {
            timetableMapper.niceTimetableToModel(it, workspaceId)
        }
    }

    @Transactional
    override fun getWeekendTimetableByUserInfo(workspaceId: String, userId: Long): BaseResponse<Timetable> {
        if (!timetableRepository.checkTimetableByWorkspaceId(workspaceId)) resetTimetable(workspaceId)

        val member = memberPort.loadMemberWithId(userId)

        val timetable = timetableRepository.findByWorkspaceId(workspaceId)

//        timetable.filter { it.classNum == me }


        return BaseResponse(
            message = "시간표 조회 성공",
            data = timetableMapper.toDomain(timetable[0])
        )
    }


}