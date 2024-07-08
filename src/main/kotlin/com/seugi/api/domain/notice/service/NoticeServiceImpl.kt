package com.seugi.api.domain.notice.service

import com.seugi.api.domain.member.adapter.out.repository.MemberRepository
import com.seugi.api.domain.notice.domain.NoticeRepository
import com.seugi.api.domain.notice.domain.mapper.NoticeMapper
import com.seugi.api.domain.notice.exception.NoticeErrorCode
import com.seugi.api.domain.notice.presentation.dto.request.CreateNoticeRequest
import com.seugi.api.domain.notice.presentation.dto.request.UpdateNoticeRequest
import com.seugi.api.domain.notice.presentation.dto.response.NoticeResponse
import com.seugi.api.domain.workspace.service.WorkspaceService
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.response.BaseResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NoticeServiceImpl(
    private val memberRepository: MemberRepository,
    private val workspaceService: WorkspaceService,
    private val noticeRepository: NoticeRepository,
    private val noticeMapper: NoticeMapper,
) : NoticeService {

    @Transactional
    override fun createNotice(createNoticeRequest: CreateNoticeRequest, userId: Long): BaseResponse<Unit> {
        val workspaceEntity = workspaceService.findWorkspaceById(createNoticeRequest.workspaceId)

        if (workspaceEntity.student.contains(userId)) throw CustomException(NoticeErrorCode.FORBIDDEN)

        val notice = noticeMapper.transferNoticeEntity(createNoticeRequest, memberRepository.findById(userId).get())
        noticeRepository.save(notice)

        return BaseResponse(
            message = "공지 등록 성공"
        )
    }

    @Transactional(readOnly = true)
    override fun getNotices(workspaceId: String, userId: Long): BaseResponse<List<NoticeResponse>> {
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
    override fun updateNotice(updateNoticeRequest: UpdateNoticeRequest, userId: Long): BaseResponse<Unit> {
        val noticeEntity = noticeRepository.findById(updateNoticeRequest.id)
            .orElseThrow { CustomException(NoticeErrorCode.NOT_FOUND) }

        noticeEntity.updateNotice(updateNoticeRequest)
        noticeRepository.save(noticeEntity)

        return BaseResponse(
            message = "공지 수정 성공"
        )
    }

    @Transactional
    override fun deleteNotice(id: Long, workspaceId: String, userId: Long): BaseResponse<Unit> {
        val workspaceEntity = workspaceService.findWorkspaceById(workspaceId)
        val notice = noticeRepository.findById(id).orElseThrow { CustomException(NoticeErrorCode.NOT_FOUND) }

        if (workspaceEntity.workspaceAdmin != userId ||
            !workspaceEntity.middleAdmin.contains(userId) ||
            notice.user!!.id != userId
        ) {
            throw CustomException(NoticeErrorCode.FORBIDDEN)
        }

        notice.deleteNotice()
        noticeRepository.save(notice)

        return BaseResponse(
            message = "공지 삭제 성공"
        )
    }

}