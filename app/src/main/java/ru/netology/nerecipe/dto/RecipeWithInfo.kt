package ru.netology.nerecipe.dto

import androidx.room.Embedded
import androidx.room.Relation
import ru.netology.nerecipe.db.CategoryOfRecipeEntity
import ru.netology.nerecipe.db.RecipeEntity
import ru.netology.nerecipe.db.StepsEntity
import ru.netology.nerecipe.db.UserEntity


data class RecipeWithInfo(
    @Embedded val recipe: Recipe,

    @Relation(parentColumn = "author_id", entityColumn = "user_id")
    val user: User,

    @Relation(parentColumn = "category_id", entityColumn = "category_id")
    val category: CategoryOfRecipe,

    @Relation(parentColumn = "recipe_id", entityColumn = "recipe_id")
    val steps: List<Steps>
)
