package com.seugi.api.domain.workspace.domain

import jakarta.persistence.*


@Entity
class WorkspaceEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val workspaceId: Long? = null,

    @Column(nullable = false)
    val workspaceName: String? = null,

    @Column(nullable = false)
    val workspaceImageUrl: String? = null,

    @Column(nullable = false)
    val workspaceAdmin: Long? = null,

    @Column(nullable = false)
    val workspaceCode: String? = null

)