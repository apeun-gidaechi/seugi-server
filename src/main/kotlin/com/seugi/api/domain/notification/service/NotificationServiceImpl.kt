package com.seugi.api.domain.notification.service

import com.seugi.api.domain.member.adapter.out.repository.MemberRepository
import com.seugi.api.domain.notification.domain.NotificationRepository
import com.seugi.api.domain.notification.domain.mapper.NotificationMapper
import com.seugi.api.domain.notification.exception.NotificationErrorCode
import com.seugi.api.domain.notification.presentation.dto.request.CreateNotificationRequest
import com.seugi.api.domain.notification.presentation.dto.request.UpdateNotificationRequest
import com.seugi.api.domain.notification.presentation.dto.response.NotificationResponse
import com.seugi.api.domain.workspace.service.WorkspaceService
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.response.BaseResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationServiceImpl(
    private val memberRepository: MemberRepository,
    private val workspaceService: WorkspaceService,
    private val noticeRepository: NotificationRepository,
    private val noticeMapper: NotificationMapper,
) : NotificationService {

    @Transactional
    override fun createNotice(createNoticeRequest: CreateNotificationRequest, userId: Long): BaseResponse<Unit> {
        val workspaceEntity = workspaceService.findWorkspaceById(createNoticeRequest.workspaceId)

        if (workspaceEntity.student.contains(userId)) throw CustomException(NotificationErrorCode.FORBIDDEN)

        val notice = noticeMapper.transferNoticeEntity(createNoticeRequest, memberRepository.findById(userId).get())
        noticeRepository.save(notice)

        return BaseResponse(
            message = "공지 등록 성공"
        )
    }

    @Transactional(readOnly = true)
    override fun getNotices(workspaceId: String, userId: Long): BaseResponse<List<NotificationResponse>> {
        return BaseResponse(
            message = "공지 불러오기 성공",
            data = noticeRepository.findByWorkspaceId(workspaceId).map {
                noticeMapper.transferNoticeResponse(
                    noticeMapper.toDomain(it)
                )
            }
        )
    }

    @Transactional
    override fun updateNotice(updateNoticeRequest: UpdateNotificationRequest, userId: Long): BaseResponse<Unit> {
        val noticeEntity = noticeRepository.findById(updateNoticeRequest.id)
            .orElseThrow { CustomException(NotificationErrorCode.NOT_FOUND) }

        noticeEntity.updateNotice(updateNoticeRequest)
        noticeRepository.save(noticeEntity)

        return BaseResponse(
            message = "공지 수정 성공"
        )
    }

    @Transactional
    override fun deleteNotice(id: Long, workspaceId: String, userId: Long): BaseResponse<Unit> {
        val workspaceEntity = workspaceService.findWorkspaceById(workspaceId)
        val notice = noticeRepository.findById(id).orElseThrow { CustomException(NotificationErrorCode.NOT_FOUND) }

        if (workspaceEntity.workspaceAdmin != userId &&
            !workspaceEntity.middleAdmin.contains(userId) &&
            notice.user!!.id != userId
        ) {
            throw CustomException(NotificationErrorCode.FORBIDDEN)
        }

        notice.deleteNotice()
        noticeRepository.save(notice)

        return BaseResponse(
            message = "공지 삭제 성공"
        )
    }

}