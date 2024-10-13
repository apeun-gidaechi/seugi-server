package com.seugi.api.domain.schedule.domain

import org.springframework.data.repository.CrudRepository

interface ScheduleRepository : CrudRepository<ScheduleEntity, Long>, ScheduleRepositoryCustom {
    override fun existsByWorkspaceId(workspaceId: String): Boolean
    override fun findByWorkspaceId(workspaceId: String): List<ScheduleEntity>
}