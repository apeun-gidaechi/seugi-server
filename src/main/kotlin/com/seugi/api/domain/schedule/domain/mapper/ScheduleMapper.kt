package com.seugi.api.domain.schedule.domain.mapper

import com.seugi.api.domain.schedule.domain.ScheduleEntity
import com.seugi.api.domain.schedule.domain.model.Schedule
import com.seugi.api.global.common.Mapper
import com.seugi.api.global.infra.nice.school.SchoolDateConvertor
import com.seugi.api.global.infra.nice.school.schedule.ScheduleRow
import org.springframework.stereotype.Component

@Component
class ScheduleMapper : Mapper<Schedule, ScheduleEntity> {

    override fun toDomain(entity: ScheduleEntity): Schedule {
        return Schedule(
            id = entity.id!!,
            workspaceId = entity.workspaceId,
            date = entity.date,
            eventName = entity.eventName,
            eventContent = entity.eventContent,
            grade = entity.grade
        )
    }

    override fun toEntity(domain: Schedule): ScheduleEntity {
        return ScheduleEntity(
            workspaceId = domain.workspaceId!!,
            date = domain.date!!,
            eventName = domain.eventName ?: "",
            eventContent = domain.eventContent ?: "",
            grade = domain.grade ?: emptyList()
        )
    }

    fun toDomain(row: List<ScheduleRow>, workspaceId: String): List<Schedule> {
        return row.map { scheduleRow ->
            val grade = listOfNotNull(
                scheduleRow.oneGradeEventYn.takeIf { it == "Y" }?.let { 1 },
                scheduleRow.twGradeEventYn.takeIf { it == "Y" }?.let { 2 },
                scheduleRow.threeGradeEventYn.takeIf { it == "Y" }?.let { 3 },
                scheduleRow.frGradeEventYn.takeIf { it == "Y" }?.let { 4 },
                scheduleRow.fivGradeEventYn.takeIf { it == "Y" }?.let { 5 },
                scheduleRow.sixGradeEventYn.takeIf { it == "Y" }?.let { 6 }
            )

            Schedule(
                workspaceId = workspaceId,
                date = SchoolDateConvertor.dateFormat(scheduleRow.aaYmd),
                eventName = scheduleRow.eventNm,
                eventContent = scheduleRow.eventCntnt,
                grade = grade
            )
        }
    }

}