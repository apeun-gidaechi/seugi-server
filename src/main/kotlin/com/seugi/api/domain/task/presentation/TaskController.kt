package com.seugi.api.domain.task.presentation

import com.seugi.api.domain.task.domain.model.ClassroomTask
import com.seugi.api.domain.task.domain.model.Task
import com.seugi.api.domain.task.presentation.dto.request.CreateTaskRequest
import com.seugi.api.domain.task.service.TaskService
import com.seugi.api.global.common.annotation.GetAuthenticatedId
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/task")
class TaskController (
    private val service: TaskService
) {

    @PostMapping()
    fun createTask(dto: CreateTaskRequest): BaseResponse<Unit> {
        return BaseResponse (
            message = "과제 만들기 성공 !",
            data = service.createTask(dto)
        )
    }

    @GetMapping("/{workspaceId}")
    fun getAllTasks(@PathVariable("workspaceId") workspaceId: String): BaseResponse<List<Task>> {
        return BaseResponse (
            message = "과제 불러오기 성공 !",
            data = service.getAllTask(workspaceId)
        )
    }

    @GetMapping("/classroom")
    suspend fun getClassroomTasks(@GetAuthenticatedId userId: Long): BaseResponse<List<ClassroomTask>> {
        return BaseResponse (
            message = "클래스룸 과제 불러오기 성공 !",
            data = service.getAllClassroomTask(userId)
        )
    }

}