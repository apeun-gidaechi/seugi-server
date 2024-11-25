package com.seugi.api.domain.schedule.domain.model

data class Schedule(
    val id: Long? = null,
    val workspaceId: String? = null,
    val date: String? = null,
    val eventName: String? = null,
    val eventContent: String? = null,
    val grade: Set<Int>? = null,
)