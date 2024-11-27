package com.seugi.api.domain.member.domain

import com.seugi.api.domain.member.domain.enums.MemberType
import jakarta.persistence.*

@Entity
class MemberEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var email: String,

    @Column(nullable = false)
    var picture: String?,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false)
    var birth: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var role: MemberType,

    @Column(nullable = false)
    var deleted: Boolean,

    @ElementCollection
    val fcmToken: MutableSet<String>

)