package com.seugi.api.domain.meal.domain.mapper

import com.seugi.api.domain.meal.domain.MealEntity
import com.seugi.api.domain.meal.domain.model.Meal
import com.seugi.api.global.common.Mapper
import org.springframework.stereotype.Component

@Component
class MealMapper : Mapper<Meal, MealEntity> {

    override fun toDomain(entity: MealEntity): Meal {
        return Meal(

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

}