package com.seugi.api.domain.oauth.adapter.out.entity

import com.seugi.api.domain.member.domain.MemberEntity
import com.seugi.api.global.auth.oauth.enums.Provider
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
class OAuthEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @OneToOne
    val member: MemberEntity,

    @NotNull
    @Enumerated(EnumType.STRING)
    val provider: Provider,

    @NotNull
    val sub: String,

    @NotNull
    var accessToken: String,

    @NotNull
    var refreshToken: String

)