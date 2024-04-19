package com.seugi.api.domain.workspace.domain.entity

import jakarta.persistence.*
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "workspace")
class WorkspaceEntity (

    @Id
    val workspaceId: ObjectId? = null,

    val workspaceName: String? = null,

    val workspaceImageUrl: String? = null,

    val workspaceAdmin: Long? = null,

    val studentWaitList: MutableSet<Long> = emptySet<Long>().toMutableSet(),

    val teacherWaitList: MutableSet<Long> = emptySet<Long>().toMutableSet(),

    val student: MutableSet<Long> = emptySet<Long>().toMutableSet(),

    val teacher: MutableSet<Long> = emptySet<Long>().toMutableSet(),

    val workspaceCode: String? = null

)