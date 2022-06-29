package ru.netology.nerecipe.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class Recipe(
    val recipeId: Long,
    val recipeName: String,
    val categoryId: Long,
    val authorName: String,
    @JsonProperty("favorites") val isFavorites: Boolean
)
