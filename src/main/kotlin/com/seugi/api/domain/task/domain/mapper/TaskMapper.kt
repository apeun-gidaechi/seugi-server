package com.seugi.api.domain.task.domain.mapper

import com.seugi.api.domain.task.domain.TaskEntity
import com.seugi.api.domain.task.domain.model.Task
import com.seugi.api.global.common.Mapper
import org.springframework.stereotype.Component

@Component
class TaskMapper : Mapper<Task, TaskEntity> {

    override fun toDomain(entity: TaskEntity): Task {
        return Task (
            id = entity.id,
            workspaceId = entity.workspaceId,
            title = entity.title,
            description = entity.description,
            dueDate = entity.dueDate
        )
    }

    override fun toEntity(domain: Task): TaskEntity {
        return TaskEntity (
            id = domain.id,
            workspaceId = domain.workspaceId,
            title = domain.title,
            description = domain.description,
            dueDate = domain.dueDate
        )
    }

}