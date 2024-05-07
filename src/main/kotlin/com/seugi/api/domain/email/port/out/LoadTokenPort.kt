package com.seugi.api.domain.email.port.out

import com.seugi.api.domain.email.adapter.out.entity.CodeEntity
import java.util.*

interface LoadTokenPort {

    fun loadToken(code: String): String

}