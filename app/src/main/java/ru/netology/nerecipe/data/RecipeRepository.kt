package ru.netology.nerecipe.data

import androidx.lifecycle.LiveData
import ru.netology.nerecipe.dto.*


interface RecipeRepository {

    val data: LiveData<List<RecipeWithInfo>>

    fun delete(recipeId: Long)

    fun save(recipe: Recipe):Long

    fun saveSteps(step: Steps)

    fun createCategory(category: List<CategoryOfRecipe>)

    fun favoritesByMe(recipeId: Long)

    fun getStepsByRecipeId(recipeId: Long): List<Steps>

    fun getRecipeById(recipeI: Long): Recipe?

    fun getAllCategory(): List<CategoryOfRecipe>

    fun getCategoryById(categoryId: Long): CategoryOfRecipe

    companion object {
        const val NEW_RECIPE_ID = 0L
    }
}