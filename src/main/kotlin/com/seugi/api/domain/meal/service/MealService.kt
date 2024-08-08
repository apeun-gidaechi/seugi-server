package com.seugi.api.domain.meal.service

import com.seugi.api.domain.meal.domain.model.MealResponse
import com.seugi.api.global.response.BaseResponse


interface MealService {
    fun resetMealByWorkspaceId(workspaceId: String): BaseResponse<Unit>
    fun getMealByDate(workspaceId: String, mealDate: String): BaseResponse<List<MealResponse>>
    fun getAllMeals(workspaceId: String): BaseResponse<List<MealResponse>>
}