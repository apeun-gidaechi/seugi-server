package seugi.server.domain.email.port.`in`

interface ConfirmTokenUseCase {

    fun confirmToken(token: String, email: String)

}