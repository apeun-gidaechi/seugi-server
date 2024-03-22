package seugi.server.global.jwt

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import seugi.server.domain.member.port.out.LoadMemberPort

@Service
class UserDetailsServiceImpl (
    private val loadMemberPort: LoadMemberPort
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        return UserDetailsImpl (
            loadMemberPort.loadMemberWithEmail(email)
        )
    }
}