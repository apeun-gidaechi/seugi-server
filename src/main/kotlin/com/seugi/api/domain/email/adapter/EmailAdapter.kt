package com.seugi.api.domain.email.adapter

import org.springframework.stereotype.Component
import com.seugi.api.domain.email.adapter.out.repository.EmailRepository
import com.seugi.api.domain.email.port.out.LoadEmailPort
import com.seugi.api.domain.email.port.out.LoadTokenPort
import com.seugi.api.domain.email.port.out.SaveEmailPort
import com.seugi.api.domain.email.port.out.SaveTokenPort
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