package com.seugi.api.domain.email.application.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import com.seugi.api.domain.email.port.`in`.SendEmailUseCase

@Service
class SendEmailService (
    private val emailSender: JavaMailSender
) : SendEmailUseCase {

    override fun sendEmail(
        to: String,
        subject: String,
        message: String
    ) {

        val mail = SimpleMailMessage()

        mail.setTo(to)
        mail.subject = subject
        mail.text = message

        emailSender.send(mail)

    }

}