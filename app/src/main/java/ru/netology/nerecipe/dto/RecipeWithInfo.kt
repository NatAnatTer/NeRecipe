package ru.netology.nerecipe.dto

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation
import ru.netology.nerecipe.db.CategoryOfRecipeEntity
import ru.netology.nerecipe.db.RecipeEntity
import ru.netology.nerecipe.db.UserEntity


data class RecipeWithInfo(
    @Embedded val recipe: RecipeEntity,

    @Relation(parentColumn = "author_id", entityColumn = "user_id")//, entity = UserEntity::class)
    val user: UserEntity,

    @Relation(parentColumn = "category_id", entityColumn = "category_id")
    val category: CategoryOfRecipeEntity
)
