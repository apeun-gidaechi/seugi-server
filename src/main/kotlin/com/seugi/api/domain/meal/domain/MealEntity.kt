package com.seugi.api.domain.meal.domain

import jakarta.persistence.*

@Entity
class MealEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val workspaceId: String,

    @Column(nullable = false)
    val mealType: String,

    @Column(nullable = false)
    val menu: String,

    @Column(nullable = false)
    val calorie: String,

    @Column(nullable = false)
    val mealInfo: String,

    @Column(nullable = false)
    val mealDate: String,

    )