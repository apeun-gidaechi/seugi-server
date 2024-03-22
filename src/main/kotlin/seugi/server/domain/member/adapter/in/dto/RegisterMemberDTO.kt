package seugi.server.domain.member.adapter.`in`.dto

data class RegisterMemberDTO (
    val name: String,
    val email: String,
    val password: String,
    val birth: String
)