package com.seugi.api.domain.workspace.domain

import com.seugi.api.domain.workspace.domain.entity.WorkspaceEntity
import com.seugi.api.domain.workspace.domain.enums.Status
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.query.Param

interface WorkspaceRepository: MongoRepository<WorkspaceEntity, ObjectId> {

    @Query("SELECT w FROM Workspace w WHERE ?1 == w.student or ?1 == w.teacher or ?1 == w.workspaceAdmin or ?1 == w.middleAdmin And w.status == ALIVE")
    fun findByUserIdAndStatus(@Param("userId") userId: Long, @Param("status") status: Status): List<WorkspaceEntity>
    fun findByWorkspaceCodeEquals(workspaceCode: String) : WorkspaceEntity?
    fun existsByWorkspaceCode(workspaceCode: String) : Boolean
}