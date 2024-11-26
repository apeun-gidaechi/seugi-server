package com.seugi.api.domain.notification.service

import com.seugi.api.domain.notification.presentation.dto.response.NotificationResponse
import com.seugi.api.domain.notification.domain.NotificationRepository
import com.seugi.api.domain.notification.domain.mapper.NotificationMapper
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.CacheEvict
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationCacheService(
    private val noticeRepository: NotificationRepository,
    private val noticeMapper: NotificationMapper
) {

    @Transactional(readOnly = true)
    @Cacheable(value = ["notifications"], key = "#workspaceId")
    fun getNotices(workspaceId: String, pageable: Pageable): List<NotificationResponse> {
        return noticeRepository.findByWorkspaceId(workspaceId, pageable).map {
            noticeMapper.transferNoticeResponse(
                noticeMapper.toDomain(it)
            )
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @CacheEvict(value = ["notifications"], key = "#workspaceId")
    fun deleteCache(workspaceId: String) {}

}