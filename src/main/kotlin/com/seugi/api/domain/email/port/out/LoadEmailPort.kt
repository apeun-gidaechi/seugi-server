package com.seugi.api.domain.email.port.out

interface LoadEmailPort {

    fun loadEmail(token: String): String

}