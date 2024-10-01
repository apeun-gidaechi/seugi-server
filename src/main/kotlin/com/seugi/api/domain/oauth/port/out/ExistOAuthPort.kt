package com.seugi.api.domain.oauth.port.out

import com.seugi.api.global.common.enums.Provider

interface ExistOAuthPort {

    fun existOAuthByMemberIdAndProvider(memberId: Long, provider: Provider): Boolean
    fun existOAuthBySubAndProvider(sub: String, provider: Provider): Boolean

}