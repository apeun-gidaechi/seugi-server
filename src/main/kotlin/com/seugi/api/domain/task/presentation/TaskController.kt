package com.seugi.api.domain.task.presentation

import com.seugi.api.domain.member.domain.model.Member
import com.seugi.api.domain.task.domain.model.ClassroomTask
import com.seugi.api.domain.task.domain.model.Task
import com.seugi.api.domain.task.presentation.dto.request.CreateTaskRequest
import com.seugi.api.domain.task.service.TaskService
import com.seugi.api.global.common.annotation.GetResolvedMember
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/task")
class TaskController (
    private val service: TaskService
) {

    @PostMapping
    fun createTask(@RequestBody dto: CreateTaskRequest): BaseResponse<Unit> {
        service.createTask(dto)
        return BaseResponse (message = "과제 만들기 성공 !")
    }

    @GetMapping("/{workspaceId}")
    fun getAllTasks(@PathVariable("workspaceId") workspaceId: String): BaseResponse<List<Task>> {
        return BaseResponse (
            message = "과제 불러오기 성공 !",
            data = service.getAllTask(workspaceId)
        )
    }

    @GetMapping("/classroom")
    suspend fun getClassroomTasks(@GetResolvedMember model: Member): BaseResponse<List<ClassroomTask>> {
        return BaseResponse (
            message = "클래스룸 과제 불러오기 성공 !",
            data = service.getAllClassroomTask(model.id)
        )
    }

}