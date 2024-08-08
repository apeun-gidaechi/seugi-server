package com.seugi.api.domain.meal.domain

interface MealRepositoryCustom {
    fun checkMeal(workspaceId: String): Boolean
    fun getMealsByDateAndWorkspaceId(mealDate: String, workspaceId: String): List<MealEntity>
    fun findAllByWorkspaceId(workspaceId: String): List<MealEntity>
    fun deleteMealByWorkspaceId(workspaceId: String)
}