package ru.netology.nerecipe.dto

data class Recipe(
    val recipeId: Long,
    val recipeName: String,
    val authorId: Long,
    val categoryId: Long,
)
