package com.seugi.api.domain.meal.service

import com.seugi.api.domain.meal.domain.model.Meal


interface MealService {
    fun resetMealByWorkspaceId(workspaceId: String)
    fun getMealByDate(workspaceId: String, mealDate: String): List<Meal>
}