package com.seugi.api.domain.email.port.out

import java.util.*

interface LoadTokenPort {

    fun loadToken(code: String): Optional<String>

}