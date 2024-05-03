package com.seugi.api.domain.member.adapter.out.repository

import java.util.Optional

interface TokenRepository {

    fun saveToken(accessToken: String, refreshToken: String)

    fun loadToken(accessToken: String): Optional<String>

}