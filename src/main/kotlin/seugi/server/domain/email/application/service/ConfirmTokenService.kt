package seugi.server.domain.email.application.service

import org.springframework.stereotype.Service
import seugi.server.domain.email.application.exception.EmailErrorCode
import seugi.server.domain.email.port.`in`.ConfirmTokenUseCase
import seugi.server.domain.email.port.out.LoadEmailPort
import seugi.server.global.exception.CustomException

@Service
class ConfirmTokenService (
    private val loadEmailPort: LoadEmailPort
) : ConfirmTokenUseCase {

    override fun confirmToken(token: String, email: String) {
        val loadEmail = loadEmailPort.loadEmail(token)
            .orElseThrow {
                CustomException(EmailErrorCode.EMAIL_NOT_LOADED)
            }

        if (loadEmail != email) {
            throw CustomException(EmailErrorCode.EMAIL_NOT_MATCH)
        }
    }

}