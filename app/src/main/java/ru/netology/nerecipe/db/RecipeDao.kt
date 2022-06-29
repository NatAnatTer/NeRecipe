package ru.netology.nerecipe.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipe")
    fun getAll(): LiveData<List<RecipeWithInfoEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: RecipeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSteps(steps: StepsEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategory(category: List<CategoryOfRecipeEntity>)

    @Query("SELECT * FROM category_of_recipe")
    fun getAllCategory(): List<CategoryOfRecipeEntity>

    @Query("SELECT * FROM category_of_recipe WHERE category_id = :categoryId")
    fun getCategoryById(categoryId: Long): CategoryOfRecipeEntity

    @Query("DELETE FROM recipe WHERE recipe_id = :id")
    fun removeRecipeById(id: Long)

    @Query("DELETE FROM steps WHERE recipe_id = :id")
    fun removeStepsById(id: Long)

    @Transaction
    fun removeRecipe(idRecipe: Long) {
        removeStepsById(idRecipe)
        removeRecipeById(idRecipe)
    }

    @Query(
        """
            UPDATE recipe SET
            is_favorites =  CASE WHEN is_favorites THEN 0 ELSE 1 END
            WHERE recipe_id = :recipeId
        """
    )
    fun favorite(recipeId: Long)

    @Query("SELECT * FROM steps WHERE recipe_id = :id")
    fun getStepsByRecipeId(id: Long): List<StepsEntity>

    @Query("SELECT * FROM recipe WHERE recipe_id = :id")
    fun getRecipeById(id: Long): RecipeEntity

}


