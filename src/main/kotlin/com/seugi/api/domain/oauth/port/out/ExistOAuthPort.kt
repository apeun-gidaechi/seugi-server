package com.seugi.api.domain.oauth.port.out

interface ExistOAuthPort {

    fun existOAuth(memberId: Long, provider: String): Boolean


}