package com.seugi.api.domain.timetable.service

import com.seugi.api.domain.timetable.domain.TimetableRepository
import com.seugi.api.domain.timetable.domain.mapper.TimetableMapper
import com.seugi.api.domain.timetable.domain.model.Timetable
import com.seugi.api.domain.workspace.service.WorkspaceService
import com.seugi.api.global.infra.nice.school.NiceSchoolService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@Service
class TimetableServiceImpl(
    private val workspaceService: WorkspaceService,
    private val timetableMapper: TimetableMapper,
    private val timetableRepository: TimetableRepository,
    private val niceSchoolService: NiceSchoolService,
) : TimetableService {

    private fun resetTimetable() {

    }


    private fun saveTimetable(timetables: List<Timetable>) {
        timetableRepository.saveAll(
            timetables.map {
                timetableMapper.toEntity(it)
            }
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

    @Transactional
    override fun getWeekendTimetable(workspaceId: String, userId: Long) {

        val schoolInfo = workspaceService.findWorkspaceById(workspaceId).schoolInfo

        val date = getDate()

        val data = niceSchoolService.getSchoolTimeTable(
            schoolInfo = schoolInfo,
            startDate = date.first,
            endDate = date.second,
        )

        if (data.isNotEmpty()) {
            saveTimetable(data)
        } else {
            saveTimetable(
                listOf(Timetable(workspaceId = workspaceId))
            )
        }
    }

}