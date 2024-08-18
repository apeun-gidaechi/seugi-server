package com.seugi.api.domain.timetable.domain

import org.springframework.data.repository.CrudRepository

interface TimetableRepository : CrudRepository<TimetableEntity, Long>