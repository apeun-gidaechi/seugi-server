package com.seugi.api.domain.member.adapter.out.entity

import jakarta.persistence.*

@Entity
data class MemberEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false)
    var email: String = "",

    @Column(nullable = false)
    var picture: String? = "",

    @Column(nullable = false)
    var password: String = "",

    @Column(nullable = false)
    var birth: String = "",

    @Column(nullable = false)
    var role: String = "ROLE_USER",

    var deleted: Boolean = false,

    @ElementCollection
    val tokenList: MutableSet<String>,

    )
