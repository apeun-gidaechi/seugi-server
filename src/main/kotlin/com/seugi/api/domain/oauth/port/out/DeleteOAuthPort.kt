package com.seugi.api.domain.oauth.port.out

import com.seugi.api.global.auth.oauth.enums.Provider

interface DeleteOAuthPort {

    fun deleteOAuth(memberId: Long, provider: Provider)

}