package com.seugi.api.domain.task.service

import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.classroom.Classroom
import com.seugi.api.domain.oauth.application.service.GoogleRefreshService
import com.seugi.api.domain.oauth.port.out.LoadOAuthPort
import com.seugi.api.domain.task.domain.TaskRepository
import com.seugi.api.domain.task.domain.mapper.TaskMapper
import com.seugi.api.domain.task.domain.model.Task
import com.seugi.api.domain.task.domain.model.ClassroomTask
import com.seugi.api.domain.task.presentation.dto.request.CreateTaskRequest
import com.seugi.api.global.auth.oauth.enums.Provider
import kotlinx.coroutines.*
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class TaskServiceImpl (
    private val repository: TaskRepository,
    private val mapper: TaskMapper,
    private val netHttpTransport: NetHttpTransport,
    private val loadOAuthPort: LoadOAuthPort,
    private val googleRefreshService: GoogleRefreshService
) : TaskService {

    private fun getClassroom(accessToken: String): Classroom {
        val requestInitializer = HttpRequestInitializer { request ->
            request.headers.authorization = "Bearer $accessToken"
        }

        return Classroom.Builder(
            netHttpTransport,
            GsonFactory.getDefaultInstance(),
            requestInitializer
        )
            .setApplicationName("seugi-server")
            .build()
    }

    private suspend fun getCourseWork(accessToken: String): List<ClassroomTask> = coroutineScope {
        val classroom = getClassroom(accessToken)
        val courses = classroom.courses().list().execute().courses

        courses.map { course ->
            async(Dispatchers.IO) {
                classroom.courses().courseWork().list(course.id).execute().courseWork.orEmpty()
            }
        }.awaitAll().flatten().map { ClassroomTask(it) }
    }

    override fun createTask(dto: CreateTaskRequest) {
        val task = Task(dto)

        repository.save(mapper.toEntity(task))
    }

    override fun getAllTask(workspaceId: String): List<Task> {
        val list = repository.findByWorkspaceId(workspaceId)

        return list.map { mapper.toDomain(it) }
    }

    override suspend fun getAllClassroomTask(userId: Long): List<ClassroomTask> {
        val oauth = loadOAuthPort.loadOAuthByMemberIdAndProvider(userId, Provider.GOOGLE)
        var list: List<ClassroomTask> = emptyList()

        try {
            list = getCourseWork(oauth.accessToken.value)
        } catch (e: GoogleJsonResponseException) {
            if (e.statusCode == 401) {
                googleRefreshService.refreshAccessToken(oauth)
                getAllClassroomTask(userId)
            }
        }

        return list
    }

}