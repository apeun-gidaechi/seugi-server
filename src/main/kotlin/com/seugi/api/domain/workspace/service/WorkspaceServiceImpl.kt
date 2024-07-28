package com.seugi.api.domain.workspace.service

import com.seugi.api.domain.member.adapter.`in`.dto.res.RetrieveMemberResponse
import com.seugi.api.domain.member.application.port.out.LoadMemberPort
import com.seugi.api.domain.profile.adapter.`in`.response.RetrieveProfileResponse
import com.seugi.api.domain.profile.application.port.out.LoadProfilePort
import com.seugi.api.domain.profile.application.service.CreateProfileService
import com.seugi.api.domain.workspace.domain.WorkspaceRepository
import com.seugi.api.domain.workspace.domain.entity.WorkspaceEntity
import com.seugi.api.domain.workspace.domain.enums.Status
import com.seugi.api.domain.workspace.domain.enums.WorkspaceRole
import com.seugi.api.domain.workspace.domain.mapper.WorkspaceMapper
import com.seugi.api.domain.workspace.exception.WorkspaceErrorCode
import com.seugi.api.domain.workspace.presentation.dto.request.*
import com.seugi.api.domain.workspace.presentation.dto.response.WorkspaceInfoResponse
import com.seugi.api.domain.workspace.presentation.dto.response.WorkspaceMemberChartResponse
import com.seugi.api.domain.workspace.presentation.dto.response.WorkspaceResponse
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.response.BaseResponse
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random


@Service
class WorkspaceServiceImpl(
    private val workspaceMapper: WorkspaceMapper,
    private val workspaceRepository: WorkspaceRepository,
    @Value("\${workspace.code.secret}") private val charset: String,
    private val createProfileService: CreateProfileService,
    private val loadProfilePort: LoadProfilePort,
    private val loadMemberPort: LoadMemberPort,
) : WorkspaceService {

    private fun genCode(length: Int = 6): String {
        return (1..length)
            .map { Random.nextInt(0, charset.length) }
            .map(charset::get)
            .joinToString("")
    }

    private fun getMemberInfo(userId: Long): RetrieveMemberResponse {
        return RetrieveMemberResponse(loadMemberPort.loadMemberWithId(userId))
    }

    private fun checkForStudent(workspaceEntity: WorkspaceEntity, userId: Long) {
        if (workspaceEntity.student.contains(userId)) throw CustomException(WorkspaceErrorCode.FORBIDDEN)
    }

    private fun validateIdLength(workspaceId: String) {
        if (workspaceId.length != 24) throw CustomException(WorkspaceErrorCode.NOT_FOUND)
    }

    override fun findWorkspaceById(id: String): WorkspaceEntity {
        validateIdLength(id)
        return workspaceRepository.findById(ObjectId(id)).orElseThrow {
            CustomException(WorkspaceErrorCode.NOT_FOUND)
        }
    }

    override fun getWorkspace(workspaceId: String, userId: Long): BaseResponse<WorkspaceResponse> {
        return BaseResponse(
            message = "워크스페이스 단건 조회 성공",
            data = workspaceMapper.toWorkspaceResponse(findWorkspaceById(workspaceId))
        )
    }

    @Transactional
    override fun createWorkspace(userId: Long, createWorkspaceRequest: CreateWorkspaceRequest): BaseResponse<String> {
        var workspaceCode: String

        do {
            workspaceCode = genCode()
        } while (workspaceRepository.existsByWorkspaceCode(workspaceCode))

        var workspaceEntity = workspaceMapper.toEntity(
            workspaceMapper.toWorkspace(createWorkspaceRequest, userId, workspaceCode)
        )

        workspaceEntity = workspaceRepository.save(workspaceEntity)

        val workspaceId = workspaceEntity.id.toString()

        createProfileService.createProfile(userId, workspaceId)

        return BaseResponse(
            message = "워크스페이스 생성 완료",
            data = workspaceId
        )
    }

    @Transactional
    override fun deleteWorkspace(userId: Long, workspaceId: String): BaseResponse<Unit> {
        val workspaceEntity: WorkspaceEntity = findWorkspaceById(workspaceId)
        if (workspaceEntity.workspaceAdmin != userId) throw CustomException(WorkspaceErrorCode.FORBIDDEN)

        workspaceEntity.status = Status.DELETE

        workspaceRepository.save(workspaceEntity)

        return BaseResponse(
            message = "워크스페이스 삭제 성공"
        )
    }

    @Transactional
    override fun updateWorkspace(userId: Long, updateWorkspaceRequest: UpdateWorkspaceRequest): BaseResponse<Unit> {

        val workspaceEntity: WorkspaceEntity = findWorkspaceById(updateWorkspaceRequest.workspaceId)
        if (workspaceEntity.workspaceAdmin != userId && !workspaceEntity.middleAdmin.contains(userId)) throw CustomException(
            WorkspaceErrorCode.FORBIDDEN
        )

        if (updateWorkspaceRequest.workspaceName.isNotEmpty()) {
            workspaceEntity.workspaceName = updateWorkspaceRequest.workspaceName
        }
        if (updateWorkspaceRequest.workspaceImgUrl.isNotEmpty()) {
            workspaceEntity.workspaceImageUrl = updateWorkspaceRequest.workspaceImgUrl
        }
        workspaceRepository.save(workspaceEntity)

        return BaseResponse(
            message = "워크스페이스 업데이트성공"
        )

    }

    override fun getMyWaitList(userId: Long): BaseResponse<List<WorkspaceInfoResponse>> {
        return BaseResponse(
            message = "자신이 대기중인 워크스페이스 모두 불러오기",
            data = workspaceRepository.findWaitWorkspaceByStatusAndUserId(Status.ALIVE, userId)
                .map { workspaceMapper.toWorkspaceInfoResponse(it) }
        )
    }

    @Transactional(readOnly = true)
    override fun getWorkspaces(userId: Long): BaseResponse<List<WorkspaceResponse>> {
        return BaseResponse(
            message = "자신이 속한 워크스페이스 전체 불러오기 성공",
            data = workspaceRepository.findOneByStatusAndUserIds(Status.ALIVE, userId)
                .map { workspaceMapper.toWorkspaceResponse(it) }
        )

    }

    @Transactional(readOnly = true)
    override fun getWorkspaceCode(userId: Long, workspaceId: String): BaseResponse<String> {

        val workspaceEntity = findWorkspaceById(workspaceId)

        if (workspaceEntity.workspaceAdmin != userId && !workspaceEntity.middleAdmin.contains(userId) && !workspaceEntity.teacher.contains(
                userId
            )
        ) throw CustomException(
            WorkspaceErrorCode.FORBIDDEN
        )
        return BaseResponse(
            message = "워크스페이스 코드 조회 성공",
            data = workspaceEntity.workspaceCode
        )

    }

    @Transactional(readOnly = true)
    override fun searchWorkspace(code: String): BaseResponse<WorkspaceInfoResponse> {

        return BaseResponse(
            message = "워크스페이스 조회 성공",
            data = workspaceMapper.toWorkspaceInfoResponse(
                workspaceRepository.findByWorkspaceCodeEquals(code)
                    ?: throw CustomException(WorkspaceErrorCode.NOT_FOUND)
            )
        )

    }

    private fun checkExistInWorkspace(userId: Long, workspace: WorkspaceEntity) {
        if (workspace.workspaceAdmin == userId || workspace.middleAdmin.contains(userId) || workspace.teacher.contains(
                userId
            ) || workspace.student.contains(userId)
        ) throw CustomException(WorkspaceErrorCode.EXIST)
    }

    @Transactional
    override fun joinWorkspace(userId: Long, joinWorkspaceRequest: JoinWorkspaceRequest): BaseResponse<Unit> {

        val workspace: WorkspaceEntity = findWorkspaceById(joinWorkspaceRequest.workspaceId)
        if (workspace.workspaceCode != joinWorkspaceRequest.workspaceCode) throw CustomException(WorkspaceErrorCode.NOT_MATCH)

        when (joinWorkspaceRequest.role) {
            WorkspaceRole.STUDENT -> {
                checkExistInWorkspace(userId, workspace)
                workspace.studentWaitList.add(userId)
            }

            WorkspaceRole.TEACHER -> {
                checkExistInWorkspace(userId, workspace)
                workspace.teacherWaitList.add(userId)
            }

            WorkspaceRole.MIDDLE_ADMIN -> Unit
        }

        workspaceRepository.save(workspace)

        return BaseResponse(
            message = "워크스페이스 참가 신청 성공"
        )
    }

    @Transactional(readOnly = true)
    override fun getWaitList(
        userId: Long,
        getWaitListRequest: GetWaitListRequest,
    ): BaseResponse<List<RetrieveMemberResponse>> {

        if (getWaitListRequest.workspaceId.isEmpty() or getWaitListRequest.role.name.isEmpty()) throw CustomException(
            WorkspaceErrorCode.MEDIA_TYPE_ERROR
        )

        val workspaceEntity: WorkspaceEntity = findWorkspaceById(getWaitListRequest.workspaceId)

        // 학생인 경우 확인 못하게 예외를 던짐
        checkForStudent(workspaceEntity = workspaceEntity, userId = userId)

        when (getWaitListRequest.role) {
            WorkspaceRole.STUDENT -> {
                return BaseResponse(
                    message = "학생 대기명단 불러오기 성공",
                    data = workspaceEntity.studentWaitList.map { getMemberInfo(it) }
                )
            }

            WorkspaceRole.TEACHER -> {
                // 워크스페이스 어드민과 중간관리자만 선생님 목록 확인 가능
                if (workspaceEntity.workspaceAdmin != userId && !workspaceEntity.middleAdmin.contains(userId)) throw CustomException(
                    WorkspaceErrorCode.FORBIDDEN
                )
                return BaseResponse(
                    message = "선생님 대기명단 불러오기 성공",
                    data = workspaceEntity.teacherWaitList.map { getMemberInfo(it) }
                )
            }

            WorkspaceRole.MIDDLE_ADMIN -> {
                if (workspaceEntity.workspaceAdmin != userId) throw CustomException(WorkspaceErrorCode.FORBIDDEN)
                return BaseResponse(
                    message = "선생님 대기명단 불러오기 성공",
                    data = workspaceEntity.middleAdminWaitList.map { getMemberInfo(it) }
                )
            }
        }
    }

    @Transactional
    override fun addWaitListToWorkspaceMember(
        userId: Long,
        waitSetWorkspaceMemberRequest: WaitSetWorkspaceMemberRequest,
    ): BaseResponse<Unit> {

        val workspaceEntity: WorkspaceEntity = findWorkspaceById(waitSetWorkspaceMemberRequest.workspaceId)

        checkForStudent(workspaceEntity = workspaceEntity, userId = userId)

        when (waitSetWorkspaceMemberRequest.role) {
            WorkspaceRole.STUDENT -> {
                workspaceEntity.studentWaitList.removeAll(waitSetWorkspaceMemberRequest.userSet)
                workspaceEntity.teacher.removeAll(waitSetWorkspaceMemberRequest.userSet)
                workspaceEntity.middleAdmin.removeAll(waitSetWorkspaceMemberRequest.userSet)
                workspaceEntity.student.addAll(waitSetWorkspaceMemberRequest.userSet)
            }

            WorkspaceRole.TEACHER -> {
                // 워크스페이스 어드민과 중간관리자만 선생님 목록 추가 가능
                if (workspaceEntity.workspaceAdmin != userId && !workspaceEntity.middleAdmin.contains(userId)) throw CustomException(
                    WorkspaceErrorCode.FORBIDDEN
                )

                workspaceEntity.teacherWaitList.removeAll(waitSetWorkspaceMemberRequest.userSet)
                workspaceEntity.student.removeAll(waitSetWorkspaceMemberRequest.userSet)
                workspaceEntity.middleAdminWaitList.removeAll(waitSetWorkspaceMemberRequest.userSet)
                workspaceEntity.teacher.addAll(waitSetWorkspaceMemberRequest.userSet)
            }

            WorkspaceRole.MIDDLE_ADMIN -> {
                //어드민만 중간 관리자 추가 가능
                if (workspaceEntity.workspaceAdmin != userId) throw CustomException(WorkspaceErrorCode.FORBIDDEN)
                workspaceEntity.middleAdminWaitList.removeAll(waitSetWorkspaceMemberRequest.userSet)
                workspaceEntity.teacher.removeAll(waitSetWorkspaceMemberRequest.userSet)
                workspaceEntity.student.removeAll(waitSetWorkspaceMemberRequest.userSet)
                workspaceEntity.middleAdmin.addAll(waitSetWorkspaceMemberRequest.userSet)
            }
        }

        workspaceRepository.save(workspaceEntity)

        createProfileService.createProfile(userId, waitSetWorkspaceMemberRequest.workspaceId)

        return BaseResponse(
            message = "${waitSetWorkspaceMemberRequest.role} 맴버 추가 성공"
        )

    }

    @Transactional
    override fun cancelWaitListToWorkspaceMember(
        userId: Long,
        waitSetWorkspaceMemberRequest: WaitSetWorkspaceMemberRequest,
    ): BaseResponse<Unit> {

        val workspaceEntity: WorkspaceEntity = findWorkspaceById(waitSetWorkspaceMemberRequest.workspaceId)

        checkForStudent(workspaceEntity = workspaceEntity, userId = userId)

        when (waitSetWorkspaceMemberRequest.role) {
            WorkspaceRole.STUDENT -> {
                workspaceEntity.studentWaitList.removeAll(waitSetWorkspaceMemberRequest.userSet)
            }

            WorkspaceRole.TEACHER -> {
                workspaceEntity.teacherWaitList.removeAll(waitSetWorkspaceMemberRequest.userSet)
            }

            WorkspaceRole.MIDDLE_ADMIN -> {
                workspaceEntity.middleAdmin.removeAll(waitSetWorkspaceMemberRequest.userSet)
            }
        }

        workspaceRepository.save(workspaceEntity)

        return BaseResponse(
            message = "취소 성공"
        )
    }

    private fun setRetrieveProfileResponse(userId: Long, workspaceId: String): RetrieveProfileResponse {
        return RetrieveProfileResponse(
            loadProfilePort.loadProfile(userId, workspaceId)
        )
    }

    @Transactional(readOnly = true)
    override fun getWorkspaceMemberChart(workspaceId: String): BaseResponse<WorkspaceMemberChartResponse> {
        validateIdLength(workspaceId)

        val workspaceEntity: WorkspaceEntity = findWorkspaceById(workspaceId)

        val response = WorkspaceMemberChartResponse()

        val adminProfile = setRetrieveProfileResponse(workspaceEntity.workspaceAdmin!!, workspaceId)
        addProfileToChart(response.admin, adminProfile)

        addProfilesToChart(response.middleAdmin, workspaceEntity.middleAdmin, workspaceId)
        addProfilesToChart(response.students, workspaceEntity.student, workspaceId)
        addProfilesToChart(response.teachers, workspaceEntity.teacher, workspaceId)

        return BaseResponse(
            message = "조직도를 불러왔습니다",
            data = response
        )
    }

    private fun addProfileToChart(
        chartSection: MutableMap<String, List<RetrieveProfileResponse>>,
        profile: RetrieveProfileResponse,
    ) {
        val belong = profile.belong
        if (belong.isNotEmpty()) {
            val existingList = chartSection[belong] ?: emptyList()
            chartSection[belong] = existingList + profile
        }
    }

    private fun addProfilesToChart(
        chartSection: MutableMap<String, List<RetrieveProfileResponse>>,
        members: MutableSet<Long>,
        workspaceId: String,
    ) {
        members.forEach { member ->
            val profile = setRetrieveProfileResponse(member, workspaceId)
            addProfileToChart(chartSection, profile)
        }
    }

    override fun getWorkspaceMemberList(workspaceId: String): BaseResponse<Set<RetrieveProfileResponse>> {
        validateIdLength(workspaceId)

        val workspaceEntity: WorkspaceEntity = findWorkspaceById(workspaceId)

        val set = HashSet<RetrieveProfileResponse>()

        set.add(setRetrieveProfileResponse(workspaceEntity.workspaceAdmin!!, workspaceId))

        workspaceEntity.middleAdmin.map {
            set.add(setRetrieveProfileResponse(it, workspaceId))
        }

        workspaceEntity.student.map {
            set.add(setRetrieveProfileResponse(it, workspaceId))
        }

        workspaceEntity.teacher.map {
            set.add(setRetrieveProfileResponse(it, workspaceId))
        }

        return BaseResponse(
            message = "멤버 전체를 조회하였습니다",
            data = set
        )
    }
}