package com.seugi.api.domain.email.port.out

interface SaveCodePort {

    fun saveCode(email: String, code: String)

}