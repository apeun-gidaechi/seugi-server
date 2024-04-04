package seugi.server.domain.email.port.out

import java.util.*

interface LoadEmailPort {

    fun loadEmail(token: String): Optional<String>

}