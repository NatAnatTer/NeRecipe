package ru.netology.nerecipe.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorites", primaryKeys = ["recipe_id", "user_id"],
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["user_id"], childColumns = ["user_id"],
        onDelete = CASCADE
    ), ForeignKey(
        entity = RecipeEntity::class,
        parentColumns = ["recipe_id"], childColumns = ["recipe_id"],
        onDelete = CASCADE
    )]
)
class FavoritesEntity(
   
    @ColumnInfo(name = "recipe_id")
    val recipeId: Long,

    @ColumnInfo(name = "user_id")
    val userId: Long
)