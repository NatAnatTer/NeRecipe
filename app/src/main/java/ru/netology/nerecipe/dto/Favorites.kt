package ru.netology.nerecipe.dto

data class Favorites(
    val favoritesId: Long,
    val recipeId: Long,
    val userId: Long
)
