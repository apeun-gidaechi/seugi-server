package seugi.server.domain.email.port.`in`

interface SendEmailUseCase {

    fun sendEmail(to: String, subject: String, message: String)

}