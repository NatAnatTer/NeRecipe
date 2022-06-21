package ru.netology.nerecipe.dto

data class Recipe(
    val recipeId: Long,
    val recipeName: String,
    val categoryId: Long,
    val authorName: String,
    val isFavorites: Boolean
)
