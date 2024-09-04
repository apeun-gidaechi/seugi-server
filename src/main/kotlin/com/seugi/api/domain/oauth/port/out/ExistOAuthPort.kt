package com.seugi.api.domain.oauth.port.out

interface ExistOAuthPort {

    fun existOAuth(id: Long, provider: String): Boolean


}