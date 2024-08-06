package com.seugi.api.domain.meal.domain

import com.querydsl.jpa.impl.JPAQueryFactory

class MealRepositoryCustomImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : MealRepositoryCustom {


    override fun checkMeal(workspaceId: String): Boolean {
        val meal: QMealEntity = QMealEntity.mealEntity

        return jpaQueryFactory
            .select(meal)
            .from(meal)
            .where(meal.workspaceId.eq(workspaceId))
            .fetchFirst() != null

    }

    override fun getMealsByDateAndWorkspaceId(mealDate: String, workspaceId: String): List<MealEntity> {
        val meal: QMealEntity = QMealEntity.mealEntity

        return jpaQueryFactory
            .select(meal)
            .from(meal)
            .where(meal.mealDate.eq(mealDate), meal.workspaceId.eq(workspaceId))
            .fetch()
    }

    override fun findAllByWorkspaceId(workspaceId: String): List<MealEntity> {
        val meal: QMealEntity = QMealEntity.mealEntity

        return jpaQueryFactory
            .select(meal)
            .from(meal)
            .where(meal.workspaceId.eq(workspaceId))
            .fetch()
    }

    override fun deleteMealByWorkspaceId(workspaceId: String) {
        val meal: QMealEntity = QMealEntity.mealEntity

        jpaQueryFactory
            .delete(meal)
            .where(meal.workspaceId.eq(workspaceId))
            .execute()
    }

}