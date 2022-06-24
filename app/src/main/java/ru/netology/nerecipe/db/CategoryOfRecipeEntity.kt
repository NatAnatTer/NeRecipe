package ru.netology.nerecipe.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "category_of_recipe")
class CategoryOfRecipeEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    val categoryId: Long,

    @ColumnInfo(name = "category_name")
    val categoryName: String
)