package com.seugi.api.domain.meal.service

import com.seugi.api.domain.meal.domain.model.MealResponse


interface MealService {
    fun resetMealByWorkspaceId(workspaceId: String)
    fun getMealByDate(workspaceId: String, mealDate: String): List<MealResponse>
    fun getAllMeals(workspaceId: String): List<MealResponse>
}