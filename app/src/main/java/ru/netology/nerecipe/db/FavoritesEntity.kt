package ru.netology.nerecipe.db

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "favorites")
class FavoritesEntity(
    @ColumnInfo(name = "recipe_id")
    val recipeId: Long,
    @ColumnInfo(name = "user_id")
    val userId: Long
)