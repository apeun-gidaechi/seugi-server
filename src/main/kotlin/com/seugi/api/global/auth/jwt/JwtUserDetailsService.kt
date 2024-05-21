package com.seugi.api.global.auth.jwt

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import com.seugi.api.domain.member.application.port.out.LoadMemberPort

@Service
class JwtUserDetailsService (
    private val loadMemberPort: LoadMemberPort
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        return JwtUserDetails (
            loadMemberPort.loadMemberWithEmail(email)
        )
    }
}