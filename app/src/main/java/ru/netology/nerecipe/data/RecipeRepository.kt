package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nerecipe.db.RecipeWithInfo
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.dto.Steps


interface RecipeRepository {

    val data: LiveData<List<RecipeWithInfo>>


    fun delete(recipeId: Long)

    fun save(recipe: Recipe, steps: Array<Steps>)

  // fun getPost(postId: Long): Post?

    companion object {
        const val NEW_POST_ID = 0L
    }
}