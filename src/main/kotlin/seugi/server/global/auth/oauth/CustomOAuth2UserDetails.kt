package seugi.server.global.auth.oauth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User
import seugi.server.domain.member.application.model.Member

class CustomOAuth2UserDetails (
    private val member: Member,
    private val attributes: MutableMap<String, Any>
):UserDetails, OAuth2User {

    override fun getAttributes(): MutableMap<String, Any> {
        return attributes
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities: MutableCollection<GrantedAuthority> = ArrayList()

        authorities.add(SimpleGrantedAuthority(member.role.value))

        return authorities
    }

    override fun getPassword(): String {
        return member.password.value
    }

    override fun getUsername(): String {
        return member.email.value
    }

    override fun getName(): String {
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