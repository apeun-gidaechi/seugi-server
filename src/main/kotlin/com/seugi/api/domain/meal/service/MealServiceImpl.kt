package com.seugi.api.domain.meal.service

import com.seugi.api.domain.meal.domain.MealEntity
import com.seugi.api.domain.meal.domain.MealRepository
import com.seugi.api.domain.meal.domain.mapper.MealMapper
import com.seugi.api.domain.meal.domain.model.Meal
import com.seugi.api.domain.meal.domain.model.MealResponse
import com.seugi.api.domain.workspace.domain.model.SchoolInfo
import com.seugi.api.domain.workspace.service.WorkspaceService
import com.seugi.api.global.infra.nice.school.NiceSchoolService
import com.seugi.api.global.response.BaseResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class MealServiceImpl(
    private val mealRepository: MealRepository,
    private val mealMapper: MealMapper,
    private val workspaceService: WorkspaceService,
    private val niceSchoolService: NiceSchoolService,
) : MealService {

    private fun getMeals(schoolInfo: SchoolInfo, startDate: String, endDate: String): List<Meal> {
        return niceSchoolService.getSchoolMeal(
            schoolInfo = schoolInfo,
            startData = startDate,
            endData = endDate
        )
    }

    private fun addWorkspaceIdToMeals(meals: List<Meal>, workspaceId: String): List<Meal> {
        return meals.map { it.copy(workspaceId = workspaceId) }
    }

    private fun addWorkspaceIdToMeal(
        schoolInfo: SchoolInfo,
        startDate: String,
        endDate: String,
        workspaceId: String,
    ): List<Meal> {
        val meals = getMeals(schoolInfo, startDate, endDate)
        return addWorkspaceIdToMeals(meals, workspaceId)
    }

    private fun saveMeals(meals: List<Meal>) {
        mealRepository.saveAll(
            meals.map {
                mealMapper.toEntity(it)
            }
        )
    }

    private fun toMealResponse(meals: List<MealEntity>): List<MealResponse> {
        return meals.map {
            mealMapper.toResponse(mealMapper.toDomain(it))
        }
    }

    @Transactional
    override fun resetMealByWorkspaceId(workspaceId: String): BaseResponse<Unit> {

        val date = LocalDate.now()
        val workspace = workspaceService.findWorkspaceById(workspaceId)

        val meals = addWorkspaceIdToMeal(
            schoolInfo = workspace.schoolInfo,
            startDate = "${date.year}${"%02d".format(date.month.value)}01",
            endDate = "${date.year}${"%02d".format(date.month.value)}${date.lengthOfMonth()}",
            workspaceId = workspaceId
        )

        saveMeals(meals)


        return BaseResponse(
            message = "급식 저장 성공"
        )

    }

    @Transactional
    override fun getMealByDate(workspaceId: String, mealDate: String): BaseResponse<List<MealResponse>> {

        if (!mealRepository.checkMeal(workspaceId)) {
            resetMealByWorkspaceId(workspaceId)
        }

        return BaseResponse(
            message = "날짜로 급식 조회 성공",
            data = toMealResponse(
                mealRepository.getMealsByDateAndWorkspaceId(
                    mealDate = mealDate,
                    workspaceId = workspaceId
                )
            )

        )


    }

    @Transactional
    override fun getAllMeals(workspaceId: String): BaseResponse<List<MealResponse>> {

        if (!mealRepository.checkMeal(workspaceId)) {
            resetMealByWorkspaceId(workspaceId)
        }

        return BaseResponse(
            message = "모든 급식 조회 성공",
            data = toMealResponse(mealRepository.findAllByWorkspaceId(workspaceId))
        )

    }


}