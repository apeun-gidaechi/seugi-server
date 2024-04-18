package com.seugi.api.domain.workspace.domain

import org.springframework.data.repository.CrudRepository

interface WorkspaceRepository: CrudRepository<WorkspaceEntity, Long> {
}