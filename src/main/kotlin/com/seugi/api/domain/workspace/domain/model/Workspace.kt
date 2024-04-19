package com.seugi.api.domain.workspace.domain.model

import org.bson.types.ObjectId

data class Workspace (
    val workspaceId: ObjectId? = null,
    val workspaceName: String? = null,
    val workspaceImageUrl: String? = null,
    val workspaceAdmin: Long? = null,
    val middleAdmin: MutableSet<Long> = emptySet<Long>().toMutableSet(),
    val middleAdminWaitList: MutableSet<Long> = emptySet<Long>().toMutableSet(),
    val studentWaitList: MutableSet<Long> = emptySet<Long>().toMutableSet(),
    val teacherWaitList: MutableSet<Long> = emptySet<Long>().toMutableSet(),
    val student: MutableSet<Long> = emptySet<Long>().toMutableSet(),
    val teacher: MutableSet<Long> = emptySet<Long>().toMutableSet(),
    val workspaceCode: String? = null,
)