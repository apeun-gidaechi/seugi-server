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