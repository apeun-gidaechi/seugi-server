package com.seugi.api.domain.email.port.`in`

interface SendEmailUseCase {

    fun sendEmail(to: String, subject: String, message: String)

}