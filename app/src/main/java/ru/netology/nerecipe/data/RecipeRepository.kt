package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nerecipe.dto.*


interface RecipeRepository {

    val data: LiveData<List<RecipeWithInfo>>

    fun delete(recipeId: Long)

    fun save(recipe: Recipe):Long
    fun saveSteps(step: Steps)

 //   fun saveRecipeSteps(recipe: Recipe, steps: List<Steps>)

    fun createCategory(category: List<CategoryOfRecipe>)

    fun favoritesByMe(recipeId: Long)

//    fun createUser(user: User)
//
//
//
//    fun getCurrentUser(userName: String): User

    companion object {
        const val NEW_RECIPE_ID = 0L

    }
}