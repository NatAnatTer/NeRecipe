package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nerecipe.dto.*


interface RecipeRepository {

    val data: LiveData<List<RecipeWithInfo>>


    fun delete(recipeId: Long)

    fun save(recipe: Recipe)
    fun saveSteps(step: Steps)

    fun createCategory(category: List<CategoryOfRecipe>)

    fun createUser(user: User)

    companion object {
        const val NEW_RECIPE_ID = 0L

    }
}