package ru.netology.nerecipe.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
@Entity
data class RecipeWithInfoEntity(
    @Embedded val recipe: RecipeEntity,

    @Relation(parentColumn = "author_id", entityColumn = "user_id")
    val user: UserEntity,

    @Relation(parentColumn = "category_id", entityColumn = "category_id")
    val category: CategoryOfRecipeEntity,

    @Relation(parentColumn = "recipe_id", entityColumn = "recipe_id")
    val steps: List<StepsEntity>
)