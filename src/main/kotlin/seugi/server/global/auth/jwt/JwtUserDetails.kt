package seugi.server.global.auth.jwt

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import seugi.server.domain.member.application.model.Member

class JwtUserDetails(
    val member: Member
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities: MutableCollection<GrantedAuthority> = ArrayList()

        authorities.add(member.role.value as GrantedAuthority)

        return authorities
    }

    override fun getPassword(): String {
        return member.password.value
    }

    override fun getUsername(): String {
        return member.name.value
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}