package com.seugi.api.domain.timetable.domain

import jakarta.persistence.*

@Entity
class TimetableEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val workspaceId: String,

    @Column(nullable = false)
    val grade: String,

    @Column(nullable = false)
    val classNum: String,

    @Column(nullable = false)
    val time: String,

    @Column(nullable = false)
    val subject: String,

    )