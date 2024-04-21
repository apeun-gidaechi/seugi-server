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

    var middleAdmin: MutableSet<Long> = emptySet<Long>().toMutableSet(),

    var middleAdminWaitList: MutableSet<Long> = emptySet<Long>().toMutableSet(),

    var studentWaitList: MutableSet<Long> = emptySet<Long>().toMutableSet(),

    var teacherWaitList: MutableSet<Long> = emptySet<Long>().toMutableSet(),

    var student: MutableSet<Long> = emptySet<Long>().toMutableSet(),

    var teacher: MutableSet<Long> = emptySet<Long>().toMutableSet(),

    val workspaceCode: String? = null,

    var status: Status

)