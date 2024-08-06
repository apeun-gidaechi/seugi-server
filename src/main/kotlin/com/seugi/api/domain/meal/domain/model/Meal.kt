package com.seugi.api.domain.meal.domain.model

data class Meal(
    val id: Long? = null,
    val workspaceId: String? = null,
    val mealType: String? = null,
    val menu: String? = null,
    val calorie: String? = null,
    val mealInfo: String? = null,
    val mealDate: String? = null,
)

data class MealResponse(
    val id: Long? = null,
    val workspaceId: String? = null,
    val mealType: String? = null,
    val menu: List<String> = emptyList(),
    val calorie: String? = null,
    val mealInfo: List<String> = emptyList(),
    val mealDate: String? = null,
)