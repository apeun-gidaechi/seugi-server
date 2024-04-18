package com.seugi.api.domain.workspace.domain

import com.seugi.api.domain.workspace.domain.entity.WorkspaceEntity
import org.springframework.data.repository.CrudRepository

interface WorkspaceRepository: CrudRepository<WorkspaceEntity, Long> {
}