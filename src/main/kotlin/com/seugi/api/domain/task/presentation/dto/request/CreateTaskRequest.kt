package com.seugi.api.domain.task.presentation.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

class CreateTaskRequest (

    @JsonProperty("workspaceId") val workspaceId: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("dueDate") val dueDate: LocalDateTime

)