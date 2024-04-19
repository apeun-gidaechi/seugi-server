package com.seugi.api.domain.workspace.service

import com.seugi.api.domain.workspace.domain.WorkspaceRepository
import com.seugi.api.domain.workspace.domain.entity.WorkspaceEntity
import com.seugi.api.domain.workspace.domain.enums.WorkspaceRole
import com.seugi.api.domain.workspace.domain.mapper.WorkspaceMapper
import com.seugi.api.domain.workspace.domain.model.Workspace
import com.seugi.api.domain.workspace.exception.WorkspaceErrorCode
import com.seugi.api.domain.workspace.presentation.dto.request.CreateWorkspaceRequest
import com.seugi.api.domain.workspace.presentation.dto.request.GetWaitListRequest
import com.seugi.api.domain.workspace.presentation.dto.request.JoinWorkspaceRequest
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.response.BaseResponse
import org.bson.types.ObjectId
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

    override fun createWorkspace(userId: Long, createWorkspaceRequest: CreateWorkspaceRequest): BaseResponse<Unit> {
        var workspaceCode: String

        do {
            workspaceCode = genCode()
        } while (workspaceRepository.findByWorkspaceCodeExists(workspaceCode))

        workspaceRepository.save(
            workspaceMapper.toEntity(
                workspaceMapper.toWorkspace(createWorkspaceRequest, userId, workspaceCode)
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

    override fun searchWorkspace(code: String): BaseResponse<Workspace> {

        return BaseResponse(
            status = HttpStatus.OK.value(),
            state = "W1",
            success = true,
            message = "워크스페이스 조회 성공",
            data = workspaceMapper.toDomain(
                workspaceRepository.findByWorkspaceCodeEquals(code) ?: throw CustomException(WorkspaceErrorCode.NOT_FOUND)
            )
        )

    }

    override fun joinWorkspace(userId: Long, joinWorkspaceRequest: JoinWorkspaceRequest): BaseResponse<Unit> {

        val id = ObjectId(joinWorkspaceRequest.workspaceId)
        val workspace: WorkspaceEntity = workspaceRepository.findById(id).orElseThrow{CustomException(WorkspaceErrorCode.NOT_FOUND)}
        if (workspace.workspaceCode==joinWorkspaceRequest.workspaceCode) throw CustomException(WorkspaceErrorCode.NOT_MATCH)


        when(joinWorkspaceRequest.role){
            WorkspaceRole.STUDENT -> workspace.studentWaitList.add(userId)
            WorkspaceRole.TEACHER -> workspace.teacherWaitList.add(userId)
        }

        workspaceRepository.save(workspace)

        return BaseResponse(
            status = HttpStatus.OK.value(),
            state = "OK",
            success = true,
            message = "워크스페이스 참가 신청 성공"
        )
    }

    override fun getWaitList(userId: Long, getWaitListRequest: GetWaitListRequest): BaseResponse<Set<Long>> {

        val workspaceId = ObjectId(getWaitListRequest.workspaceId)

        val workspaceEntity: WorkspaceEntity = workspaceRepository.findById(workspaceId).orElseThrow {
            CustomException(
                WorkspaceErrorCode.NOT_FOUND
            )
        }
        if (workspaceEntity.workspaceAdmin==userId) throw CustomException(WorkspaceErrorCode.FORBIDDEN)

        when(getWaitListRequest.role) {
            WorkspaceRole.STUDENT -> {
                return BaseResponse(
                    status = HttpStatus.OK.value(),
                    state = "OK",
                    success = true,
                    message = "학생 대기명단 불러오기 성공",
                    data =  workspaceEntity.studentWaitList
                )
            }
            WorkspaceRole.TEACHER -> {
                return BaseResponse(
                    status = HttpStatus.OK.value(),
                    state = "OK",
                    success = true,
                    message = "선생님 대기명단 불러오기 성공",
                    data = workspaceEntity.teacherWaitList
                )
            }
        }


    }

    override fun addWaitListToWorkspaceMember(userId: Long, waitSetWorkspaceMemberRequest: WaitSetWorkspaceMemberRequest): BaseResponse<Unit> {

        val workspaceId = ObjectId(waitSetWorkspaceMemberRequest.workspaceId)
        val workspaceEntity: WorkspaceEntity = workspaceRepository.findById(workspaceId).orElseThrow {
            CustomException(WorkspaceErrorCode.NOT_FOUND)
        }

        if (workspaceEntity.workspaceAdmin!=userId) throw CustomException(WorkspaceErrorCode.FORBIDDEN)

        when(waitSetWorkspaceMemberRequest.role){
            WorkspaceRole.STUDENT -> {
                workspaceEntity.studentWaitList.removeAll(waitSetWorkspaceMemberRequest.approvalUserSet)
                workspaceEntity.student.addAll(waitSetWorkspaceMemberRequest.approvalUserSet)
            }
            WorkspaceRole.TEACHER -> {
                workspaceEntity.teacherWaitList.removeAll(waitSetWorkspaceMemberRequest.approvalUserSet)
                workspaceEntity.teacher.addAll(waitSetWorkspaceMemberRequest.approvalUserSet)
            }
        }

        return BaseResponse(
            status = HttpStatus.OK.value(),
            state = "OK",
            success = true,
            message = "${waitSetWorkspaceMemberRequest.role} 맴버 추가 성공"
        )

    }

}