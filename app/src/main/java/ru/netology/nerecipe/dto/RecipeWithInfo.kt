package ru.netology.nerecipe.dto

import androidx.room.Embedded
import androidx.room.Relation



data class RecipeWithInfo(
    @Embedded val recipe: Recipe,


    @Relation(parentColumn = "category_id", entityColumn = "category_id")
    val category: CategoryOfRecipe,

    @Relation(parentColumn = "recipe_id", entityColumn = "recipe_id")
    var steps: List<Steps>
)
