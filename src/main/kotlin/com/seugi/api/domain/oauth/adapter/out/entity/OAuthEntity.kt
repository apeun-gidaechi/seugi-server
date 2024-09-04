package com.seugi.api.domain.oauth.adapter.out.entity

import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import org.jetbrains.annotations.NotNull

@Entity
class OAuthEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @OneToOne
    @JoinColumn(name = "member_id")
    val memberId: MemberEntity,

    @NotNull
    val provider: String,

    @NotNull
    var accessToken: String,

    @NotNull
    var refreshToken: String

)