package seugi.server.domain.member.adapter.out.entity

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import seugi.server.domain.member.application.model.value.MemberProfile

@Entity
data class MemberEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false)
    var email: String = "",

    @Column(nullable = false)
    var password: String = "",

    @Column(nullable = false)
    var birth: String = "",

    @Embedded
    val profile: MemberProfile,

    @Column(nullable = false)
    var role: String = "ROLE_USER",

    var loginId: String = "",

    var provider: String = "",

    var providerId: String = ""

)