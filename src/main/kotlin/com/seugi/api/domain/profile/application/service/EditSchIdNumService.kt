package com.seugi.api.domain.profile.application.service

import com.seugi.api.domain.profile.adapter.`in`.request.EditSchIdNumRequest
import com.seugi.api.domain.profile.application.port.`in`.EditSchIdNumUseCase
import com.seugi.api.domain.profile.application.port.out.LoadProfilePort
import com.seugi.api.domain.profile.application.port.out.SaveProfilePort
import com.seugi.api.domain.workspace.exception.WorkspaceErrorCode
import com.seugi.api.domain.workspace.service.WorkspaceService
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.response.BaseResponse
import org.springframework.stereotype.Service

@Service
class EditSchIdNumService (
    private val loadProfilePort: LoadProfilePort,
    private val saveProfilePort: SaveProfilePort,
    private val workspaceService: WorkspaceService
) : EditSchIdNumUseCase {

    override fun editSchIdNum(dto: EditSchIdNumRequest, workspaceId: String, id: Long): BaseResponse<Unit> {
        val permission = workspaceService.getMyPermission(id, workspaceId)

        if (permission.data == "STUDENT") {
            throw CustomException(WorkspaceErrorCode.FORBIDDEN)
        }

        val profile = loadProfilePort.loadProfile(dto.id, workspaceId)

        profile.editSchIdNum(dto)

        saveProfilePort.saveProfile(profile)

        return BaseResponse (
            message = "학번 수정 성공 !!"
        )
    }
}