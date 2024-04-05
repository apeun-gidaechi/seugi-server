package seugi.server.domain.email.adapter

import org.springframework.stereotype.Component
import seugi.server.domain.email.adapter.out.repository.EmailRepository
import seugi.server.domain.email.port.out.LoadEmailPort
import seugi.server.domain.email.port.out.LoadTokenPort
import seugi.server.domain.email.port.out.SaveEmailPort
import seugi.server.domain.email.port.out.SaveTokenPort
import java.util.Optional

@Component
class EmailAdapter (
    private val emailRepository: EmailRepository
) : SaveTokenPort, LoadTokenPort, SaveEmailPort, LoadEmailPort {

    override fun saveToken(code: String, token: String) {
        emailRepository.saveToken(code, token)
    }

    override fun loadToken(code: String): Optional<String> {
        return emailRepository.getToken(code)
    }

    override fun saveEmail(token: String, email: String) {
        return emailRepository.saveEmail(token, email)
    }

    override fun loadEmail(token: String): Optional<String> {
        return emailRepository.loadEmail(token)
    }

}