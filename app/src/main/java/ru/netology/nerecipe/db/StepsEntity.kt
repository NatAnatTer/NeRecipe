package ru.netology.nerecipe.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import ru.netology.nerecipe.dto.Recipe


@Entity(tableName = "steps",foreignKeys = [ForeignKey(
    entity = RecipeEntity::class,
    parentColumns = ["recipe_id"], childColumns = ["recipe_id"],
    onDelete = CASCADE
)])
class StepsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "step_id")
    val stepId: Long,
    @ColumnInfo(name = "number_of_step")
    val numberOfStep: Int,
    @ColumnInfo(name = "content_of_step")
    val contentOfStep: String,
    @ColumnInfo(name = "recipe_id")
    val recipeId: Long,
    @ColumnInfo(name = "image_url")
    val imageUrl: String?

)