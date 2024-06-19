package com.seugi.api.domain.workspace.domain

import com.seugi.api.domain.workspace.domain.entity.WorkspaceEntity
import com.seugi.api.domain.workspace.domain.enums.Status
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface WorkspaceRepository: MongoRepository<WorkspaceEntity, ObjectId> {

    @Query(value = "{ 'status' : ?0, '\$or' : [{'workspaceAdmin' : ?1}, {'middleAdmin' : ?1}, {'student' : ?1}, {'teacher' : ?1}]}")
    fun findOneByStatusAndUserIds(status: Status, userId: Long): List<WorkspaceEntity>

    @Query(value = "{ 'status' : ?0, '\$or' : [{'middleAdminWaitList' : ?1}, {'studentWaitList' : ?1}, {'teacherWaitList' : ?1}]}")
    fun findWaitWorkspaceByStatusAndUserId(status: Status, userId: Long): List<WorkspaceEntity>
    fun findByWorkspaceCodeEquals(workspaceCode: String) : WorkspaceEntity?
    fun existsByWorkspaceCode(workspaceCode: String) : Boolean
}