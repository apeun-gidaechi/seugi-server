package com.seugi.api.domain.email.port.out

interface LoadCodePort {

    fun loadCode(email: String): String

}