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
        mealService.resetMealByWorkspaceId(workspaceId = workspaceId)
        return BaseResponse(
            message = "급식 저장 성공"
        )
    }

    @GetMapping
    fun getMealByDate(
        @RequestParam("workspaceId") workspaceId: String,
        @RequestParam("date") date: String,
    ): BaseResponse<List<MealResponse>> {
        return BaseResponse(
            message = "날짜로 급식 조회 성공",
            data = mealService.getMealByDate(
                workspaceId = workspaceId,
                mealDate = date
            )
        )
    }

    @GetMapping("/all")
    fun getAllMeals(
        @RequestParam("workspaceId") workspaceId: String,
    ): BaseResponse<List<MealResponse>> {
        return BaseResponse(
            message = "모든 급식 조회 성공",
            data = mealService.getAllMeals(
                workspaceId = workspaceId
            )
        )
    }

}