package com.seugi.api.domain.email.port.out

interface SaveTokenPort {

    fun saveToken(code: String, token: String)

}