package ru.netology.nerecipe.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction

@Dao
 abstract class RecipeStepDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(recipe: RecipeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSteps(steps: List<StepsEntity>)

    @Transaction
    fun insertRecipeAndStep(recipe: RecipeEntity, steps: List<StepsEntity>) {
        insert(recipe)
        insertSteps(steps)
    }
}