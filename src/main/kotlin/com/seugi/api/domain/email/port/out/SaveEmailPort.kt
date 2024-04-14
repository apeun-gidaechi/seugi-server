package com.seugi.api.domain.email.port.out

interface SaveEmailPort {

    fun saveEmail(token: String, email: String)

}