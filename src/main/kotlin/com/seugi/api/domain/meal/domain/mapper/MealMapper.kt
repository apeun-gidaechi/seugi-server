package com.seugi.api.domain.meal.domain.mapper

import com.seugi.api.domain.meal.domain.MealEntity
import com.seugi.api.domain.meal.domain.model.Meal
import com.seugi.api.domain.meal.domain.model.MealResponse
import com.seugi.api.global.common.Mapper
import org.springframework.stereotype.Component

@Component
class MealMapper : Mapper<Meal, MealEntity> {

    override fun toDomain(entity: MealEntity): Meal {
        return Meal(
            id = entity.id,
            workspaceId = entity.workspaceId,
            mealType = entity.mealType,
            menu = entity.menu,
            calorie = entity.calorie,
            mealInfo = entity.mealInfo,
            mealDate = entity.mealDate
        )
    }

    override fun toEntity(domain: Meal): MealEntity {
        return MealEntity(
            workspaceId = domain.workspaceId!!,
            mealType = domain.mealType ?: "",
            menu = domain.menu ?: "",
            calorie = domain.calorie ?: "",
            mealInfo = domain.mealInfo ?: "",
            mealDate = domain.mealDate ?: ""
        )
    }

    fun toResponse(meal: Meal): MealResponse {
        return MealResponse(
            id = meal.id,
            workspaceId = meal.workspaceId,
            menu = meal.menu?.split("<br/>") ?: emptyList(),
            calorie = meal.calorie,
            mealInfo = meal.mealInfo?.split("<br/>") ?: emptyList(),
            mealDate = meal.mealDate
        )
    }

}