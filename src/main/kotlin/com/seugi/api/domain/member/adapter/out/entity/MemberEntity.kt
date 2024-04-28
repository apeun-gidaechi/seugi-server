package com.seugi.api.domain.member.adapter.out.entity

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import com.seugi.api.domain.member.application.model.value.MemberProfile

@Entity
data class MemberEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false)
    var email: String = "",

    @Column(nullable = false)
    var picture: String = "",

    @Column(nullable = false)
    var password: String = "",

    @Column(nullable = false)
    var birth: String = "",

    @Embedded
    @Column(nullable = false)
    val profile: MemberProfile,

    @Column(nullable = false)
    var role: String = "ROLE_USER",

    var loginId: String = "",

    var provider: String = "",

    var providerId: String = ""

)