package com.seugi.api.domain.oauth.port.out

import com.seugi.api.domain.oauth.application.model.OAuth
import com.seugi.api.global.common.enums.Provider

interface LoadOAuthPort {

    fun loadOAuthByMemberIdAndProvider(memberId: Long, provider: Provider): OAuth
    fun loadOAuthByProviderAndSub(provider: Provider, sub: String): OAuth

}