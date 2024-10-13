package com.seugi.api.domain.schedule.domain

interface ScheduleRepositoryCustom {
    fun existsByWorkspaceId(workspaceId: String): Boolean
    fun findByWorkspaceId(workspaceId: String): List<ScheduleEntity>
    fun findByMonth(workspaceId: String, month: Int): List<ScheduleEntity>
}