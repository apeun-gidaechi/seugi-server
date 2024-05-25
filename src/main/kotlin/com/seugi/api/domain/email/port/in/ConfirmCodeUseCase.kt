package com.seugi.api.domain.email.port.`in`

interface ConfirmCodeUseCase {

    fun confirmCode (email: String, code: String)

}