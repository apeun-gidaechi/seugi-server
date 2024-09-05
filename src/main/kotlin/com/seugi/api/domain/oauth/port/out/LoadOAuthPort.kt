package com.seugi.api.domain.oauth.port.out

import com.seugi.api.domain.oauth.application.model.OAuth

interface LoadOAuthPort {

    fun loadOAuth(memberId: Long, provider: String): OAuth

}