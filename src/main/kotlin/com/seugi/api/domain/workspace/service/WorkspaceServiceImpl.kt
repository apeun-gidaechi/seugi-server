package com.seugi.api.domain.workspace.service

import com.seugi.api.domain.workspace.domain.WorkspaceRepository
import com.seugi.api.domain.workspace.domain.mapper.WorkspaceMapper
import com.seugi.api.domain.workspace.domain.model.Workspace
import com.seugi.api.domain.workspace.presentation.dto.request.CreateWorkspace
import com.seugi.api.global.response.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class WorkspaceServiceImpl(
    private val workspaceMapper: WorkspaceMapper,
    private val workspaceRepository: WorkspaceRepository
): WorkspaceService {

     private fun genCode(length: Int = 6): String {
         val charset = "J2nUV6WX7TKAt5Thiu3NOLPZaBI4DMHoxyzQpqSkrs9ef0g8bcd0EF1GvwRlmC"
         return (1..length)
             .map { Random.nextInt(0, charset.length) }
             .map(charset::get)
             .joinToString("")
     }

    override fun createWorkspace(userId: Long, createWorkspace: CreateWorkspace): BaseResponse<Unit> {
        val workspaceCode: String = genCode()

        workspaceRepository.save(
            workspaceMapper.toEntity(
                workspaceMapper.toWorkspace(createWorkspace, userId, workspaceCode)
            )
        )

        return BaseResponse(
            status = HttpStatus.OK.value(),
            state = "W1",
            success = true,
            message = "워크스페이스 생성 완료"
        )
    }

     override fun getWorkspace(userId: Long): BaseResponse<List<Workspace>> {

         return BaseResponse(
             status = HttpStatus.OK.value(),
             state = "W1",
             success = true,
             message = "자신이 속한 워크스페이스 전체 불러오기 성공",
             data = workspaceRepository.findByStudentEqualsAndTeacherEquals(userId)
                 .map { workspaceMapper.toDomain(it) }
         )

     }

}