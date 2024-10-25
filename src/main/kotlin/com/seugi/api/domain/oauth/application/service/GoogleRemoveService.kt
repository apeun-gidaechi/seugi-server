package com.seugi.api.domain.oauth.application.service

import com.seugi.api.domain.oauth.application.exception.OAuthErrorCode
import com.seugi.api.domain.oauth.port.`in`.GoogleRemoveUseCase
import com.seugi.api.domain.oauth.port.out.DeleteOAuthPort
import com.seugi.api.domain.oauth.port.out.ExistOAuthPort
import com.seugi.api.global.auth.oauth.enums.Provider
import com.seugi.api.global.exception.CustomException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class GoogleRemoveService (
    private val existOAuthPort: ExistOAuthPort,
    private val deleteOAuthPort: DeleteOAuthPort
) : GoogleRemoveUseCase {

    @Transactional
    override fun remove(userId: Long) {
        if (!existOAuthPort.existOAuthByMemberIdAndProvider(userId, Provider.GOOGLE)) {
            throw CustomException(OAuthErrorCode.OAUTH_NOT_FOUND)
        }

        deleteOAuthPort.deleteOAuth(userId, Provider.GOOGLE)
    }

}