package com.seugi.api.domain.workspace.domain.entity

import com.seugi.api.domain.workspace.domain.enums.Status
import jakarta.persistence.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "workspace")
class WorkspaceEntity (

    @Id
    val id: ObjectId? = null,

    var workspaceName: String? = null,

    var workspaceImageUrl: String? = null,

    var workspaceAdmin: Long? = null,

    val middleAdmin: MutableSet<Long> = emptySet<Long>().toMutableSet(),

    val middleAdminWaitList: MutableSet<Long> = emptySet<Long>().toMutableSet(),

    val studentWaitList: MutableSet<Long> = emptySet<Long>().toMutableSet(),

    val teacherWaitList: MutableSet<Long> = emptySet<Long>().toMutableSet(),

    val student: MutableSet<Long> = emptySet<Long>().toMutableSet(),

    val teacher: MutableSet<Long> = emptySet<Long>().toMutableSet(),

    val workspaceCode: String? = null,

    var status: Status

)