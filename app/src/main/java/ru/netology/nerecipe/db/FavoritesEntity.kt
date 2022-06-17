package ru.netology.nerecipe.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorites", foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["user_id"], childColumns = ["user_id"]
    ), ForeignKey(
        entity = RecipeEntity::class,
        parentColumns = ["recipe_id"], childColumns = ["recipe_id"]
    )]
)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "favorites_Id")
    val favoritesId: Long,

    @ColumnInfo(name = "recipe_id")
    val recipeId: Long,

    @ColumnInfo(name = "user_id")
    val userId: Long
)