package ru.netology.nerecipe.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "recipe", foreignKeys = [ ForeignKey(
        entity = CategoryOfRecipeEntity::class,
        parentColumns = ["category_id"], childColumns = ["category_id"]
    )]

)
class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recipe_id")
    val recipeId: Long,
    @ColumnInfo(name = "recipe_name")
    val recipeName: String,
    @ColumnInfo(name = "author_name")
    val authorName: String,
    @ColumnInfo(name = "category_id")
    val categoryId: Long,
    @ColumnInfo(name = "is_favorites")
    val isFavorites: Boolean
    )