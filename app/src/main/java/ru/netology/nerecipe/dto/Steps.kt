package ru.netology.nerecipe.dto

data class Steps(
    val stepId: Long,
    val numberOfStep: Int,
    val contentOfStep: String,
    val recipeId: Long,
    val imageUrl: String
    )
