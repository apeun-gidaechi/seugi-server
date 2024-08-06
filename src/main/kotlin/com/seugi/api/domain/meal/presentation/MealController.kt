package com.seugi.api.domain.meal.presentation

import com.seugi.api.domain.meal.domain.model.MealResponse
import com.seugi.api.domain.meal.service.MealService
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/meal")
class MealController(
    private val mealService: MealService,
) {

    @PostMapping("/reset/{workspaceId}")
    fun resetMeal(
        @PathVariable workspaceId: String,
    ): BaseResponse<Unit> {
        return mealService.resetMealByWorkspaceId(
            workspaceId = workspaceId
        )
    }

    @GetMapping
    fun getMealByDate(
        @RequestParam("workspaceId") workspaceId: String,
        @RequestParam("date") date: String,
    ): BaseResponse<List<MealResponse>> {
        return mealService.getMealByDate(
            workspaceId = workspaceId,
            mealDate = date
        )
    }

    @GetMapping("/all")
    fun getAllMeals(
        @RequestParam("workspaceId") workspaceId: String,
    ): BaseResponse<List<MealResponse>> {
        return mealService.getAllMeals(
            workspaceId = workspaceId
        )
    }

}