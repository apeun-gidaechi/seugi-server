package com.seugi.api.domain.schedule.domain

import jakarta.persistence.*

@Entity
class ScheduleEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val workspaceId: String,

    @Column(nullable = false)
    val date: String,

    @Column(nullable = false)
    val eventName: String,

    @Column(nullable = false)
    val eventContent: String,

    @ElementCollection
    val grade: Set<Int>,

    )