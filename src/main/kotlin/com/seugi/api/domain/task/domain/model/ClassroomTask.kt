package com.seugi.api.domain.task.domain.model

import com.google.api.services.classroom.model.CourseWork
import java.time.LocalDateTime

data class ClassroomTask (

    val id: String,
    val title: String,
    val description: String? = "",
    val link: String,
    val dueDate: LocalDateTime?

) {

    constructor(courseWork: CourseWork): this(
        id = courseWork.id,
        title = courseWork.title,
        description = courseWork.description,
        link = courseWork.alternateLink,
        dueDate = if (courseWork.dueDate != null) LocalDateTime.of(
            courseWork.dueDate.year,
            courseWork.dueDate.month,
            courseWork.dueDate.day,
            courseWork.dueTime?.hours ?: 0,
            courseWork.dueTime?.minutes ?: 0,
            courseWork.dueTime?.seconds ?: 0
        ) else null
    )

}