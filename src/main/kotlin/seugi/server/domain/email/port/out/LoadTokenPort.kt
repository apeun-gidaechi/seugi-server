package seugi.server.domain.email.port.out

import java.util.Optional

interface LoadTokenPort {

    fun loadToken(code: String): Optional<String>

}