package ru.netology.nerecipe.dto

import androidx.room.ColumnInfo
import androidx.room.Relation
import ru.netology.nerecipe.db.CategoryOfRecipeEntity
import ru.netology.nerecipe.db.UserEntity


class RecipeWithInfo(
    @ColumnInfo(name = "recipe_id")
    val recipeId: Long,
    @ColumnInfo(name = "recipe_name")
    val recipeName: String,
    @ColumnInfo(name = "author_id")
    val authorId: Long,

    @Relation(parentColumn = "author_id", entityColumn = "author_id", entity = UserEntity::class)
  //  @ColumnInfo(name = "user_name")
    val userName: String,

    @ColumnInfo(name = "category_id")
    val categoryId: Long,

    @Relation(parentColumn = "category_id", entityColumn = "category_id", entity = CategoryOfRecipeEntity::class)
 //   @ColumnInfo(name = "category_name")
    val categoryName: String
)
