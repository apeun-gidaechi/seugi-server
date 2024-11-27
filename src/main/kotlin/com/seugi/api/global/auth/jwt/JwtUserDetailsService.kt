package com.seugi.api.global.auth.jwt

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import com.seugi.api.domain.member.service.MemberService

@Service
class JwtUserDetailsService (private val service: MemberService) {

    fun loadUserById(id: Long): UserDetails {
        return JwtUserDetails( service.findById(id) )
    }

}