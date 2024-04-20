package com.seugi.api.domain.workspace.domain.model

import com.seugi.api.domain.workspace.domain.enums.Status
import org.bson.types.ObjectId

data class Workspace (
    val workspaceId: String? = null,
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
    val workspaceStatus: Status = Status.ALIVE
)