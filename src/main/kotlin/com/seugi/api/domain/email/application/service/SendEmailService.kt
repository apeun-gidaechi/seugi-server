package com.seugi.api.domain.email.application.service

import com.seugi.api.domain.email.application.exception.EmailErrorCode
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import com.seugi.api.domain.email.port.`in`.SendEmailUseCase
import com.seugi.api.global.exception.CustomException

/* todo: SES 사용 */
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