package com.seugi.api.domain.timetable.domain.model

data class Timetable(
    val id: Long? = null,
    val workspaceId: String? = null,
    val grade: String? = null,
    val classNum: String? = null,
    val time: String? = null,
    val subject: String? = null,
    val date: String? = null,
)
