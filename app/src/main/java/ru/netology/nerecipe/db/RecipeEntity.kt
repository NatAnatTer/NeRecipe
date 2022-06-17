package ru.netology.nerecipe.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nerecipe.dto.CategoryOfRecipe


@Entity(tableName = "recipe")
class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recipe_id")
    val recipeId: Long,
    @ColumnInfo(name = "recipe_name")
    val recipeName: String,
    @ColumnInfo(name = "author_id")
    val authorId: Long,
    @ColumnInfo(name = "category_id")
    val categoryId: Long,

    )