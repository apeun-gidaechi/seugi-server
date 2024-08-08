package com.seugi.api.domain.meal.domain

import org.springframework.data.repository.CrudRepository

interface MealRepository : CrudRepository<MealEntity, Long>, MealRepositoryCustom