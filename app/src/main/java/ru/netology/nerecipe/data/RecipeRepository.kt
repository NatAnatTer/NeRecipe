package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.dto.RecipeWithInfo
import ru.netology.nerecipe.dto.Steps


interface RecipeRepository {

    val data: LiveData<List<RecipeWithInfo>>


    fun delete(recipeId: Long)

    fun save(recipe: Recipe)
    fun saveSteps(step: Steps)


    companion object {
        const val NEW_RECIPE_ID = 0L
    }
}