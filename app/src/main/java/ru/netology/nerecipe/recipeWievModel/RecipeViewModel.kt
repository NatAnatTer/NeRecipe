package ru.netology.nerecipe.recipeWievModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nerecipe.data.RecipeRepositoryImpl
import ru.netology.nerecipe.db.AppDb
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.dto.Steps
import ru.netology.nerecipe.util.SingleLiveEvent
import ru.netology.nmedia.adapter.RecipeInteractionListener
import ru.netology.nmedia.data.RecipeRepository
import ru.netology.nmedia.data.RecipeRepository.Companion.NEW_POST_ID
import java.util.*


class RecipeViewModel(application: Application) : AndroidViewModel(application),
    RecipeInteractionListener {
    private val repository: RecipeRepository = RecipeRepositoryImpl(
        dao = AppDb.getInstance(context = application).recipeDao
    )
    val data by repository::data
    private val currentRecipe = MutableLiveData<Recipe?>(null)

    val navigateToRecipeContentScreenEvent = SingleLiveEvent<Long>()
    private val navigateToShowRecipe = SingleLiveEvent<Long>()


    override fun onFavoriteClicked(recipe: Recipe){TODO()} //= repository.favorite(recipe.recipeId)
    override fun onRemoveClicked(recipe: Recipe) = repository.delete(recipe.recipeId)
    override fun onEditClicked(recipe: Recipe) {
        currentRecipe.value = recipe
        navigateToRecipeContentScreenEvent.value = recipe.recipeId
    }

    override fun onShowRecipeClicked(recipe: Recipe) {
        navigateToShowRecipe.value = recipe.recipeId
    }

    fun onAddButtonClicked() {
        navigateToRecipeContentScreenEvent.call()
    }

    fun onSaveButtonClicked(
        recipeName: String,
        authorId: Long,
        categoryId: Long,
        steps: Array<Steps>
    ) {
        if (steps.isEmpty()) return

        val newRecipe = currentRecipe.value?.copy(
            recipeName = recipeName,
            categoryId = categoryId,

            ) ?: Recipe(
            recipeId = NEW_POST_ID,
            recipeName = recipeName,
            categoryId = categoryId,
            authorId = authorId
        )
    //    repository.saveSteps(steps)
        repository.save(newRecipe, steps)
        currentRecipe.value = null
    }
}