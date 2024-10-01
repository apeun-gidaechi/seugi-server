package com.seugi.api.domain.oauth.port.out

import com.seugi.api.global.auth.oauth.enums.Provider

interface ExistOAuthPort {

    fun existOAuthByMemberIdAndProvider(memberId: Long, provider: Provider): Boolean

}