package com.seugi.api.domain.email.port.out

interface LoadTokenPort {

    fun loadToken(code: String): String

}