package ru.netology.nerecipe.recipeWievModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nerecipe.adapter.RecipeInteractionListener
import ru.netology.nerecipe.data.RecipeRepositoryImpl
import ru.netology.nerecipe.db.AppDb
import ru.netology.nerecipe.dto.CategoryOfRecipe
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.dto.Steps
import ru.netology.nerecipe.util.SingleLiveEvent
import ru.netology.nerecipe.data.RecipeRepository
import ru.netology.nerecipe.data.RecipeRepository.Companion.NEW_RECIPE_ID
import ru.netology.nerecipe.dto.RecipeWithInfo


class RecipeViewModel(application: Application) : AndroidViewModel(application),
    RecipeInteractionListener {
    private val repository: RecipeRepository = RecipeRepositoryImpl(
        dao = AppDb.getInstance(context = application).recipeDao
    )
    val data by repository::data
    private val currentRecipe = MutableLiveData<Recipe?>(null)

    val navigateToRecipeChangeContentScreenEvent = SingleLiveEvent<Long>()
    val navigateToShowRecipe = SingleLiveEvent<Long>()

     val currentStep = MutableLiveData<Steps?>(null)
    val currentSteps = MutableLiveData<List<Steps>?>(null)


    override fun onFavoriteClicked(recipe: Recipe) {
        repository.favoritesByMe(recipe.recipeId)
    }

    override fun onRemoveClicked(recipe: Recipe) = repository.delete(recipe.recipeId)
    override fun onEditClicked(recipe: Recipe) {
        currentRecipe.value = recipe
        currentSteps.value = getStepsByRecipeId(recipe.recipeId)
        navigateToRecipeChangeContentScreenEvent.value = recipe.recipeId
    }

    override fun onShowRecipeClicked(recipe: Recipe) {
        navigateToShowRecipe.value = recipe.recipeId
    }

    override fun onStepClicked(step: Steps) {
        currentStep.value = step
    }

    override fun onAddButtonClicked() {
        navigateToRecipeChangeContentScreenEvent.call()
    }

    fun createCategory(category: List<CategoryOfRecipe>) {
        repository.createCategory(category)
    }

    fun getStepsByRecipeId(recipeId: Long): List<Steps> = repository.getStepsByRecipeId(recipeId)

    fun getRecipeById(recipeId: Long): Recipe? = repository.getRecipeById(recipeId)

    fun getAllCategory(): List<CategoryOfRecipe> = repository.getAllCategory()

    fun getCategoryById(categoryId: Long): CategoryOfRecipe = repository.getCategoryById(categoryId)

    fun onSaveButtonClicked(
        newRecipeContent: RecipeWithInfo
    ) {
        if (newRecipeContent.steps.isEmpty()) return

        val newRecipe = currentRecipe.value?.copy(
            recipeName = newRecipeContent.recipe.recipeName,
            categoryId = newRecipeContent.recipe.categoryId,

            ) ?: Recipe(
            recipeId = NEW_RECIPE_ID,
            recipeName = newRecipeContent.recipe.recipeName,
            categoryId = newRecipeContent.recipe.categoryId,
            authorName = newRecipeContent.recipe.authorName,
            isFavorites = false
        )
         val recipeNewId = repository.save(newRecipe)
        newRecipeContent.steps.forEach {

              repository.saveSteps(Steps(
                  stepId = 0L,
                  numberOfStep = it.numberOfStep,
                  contentOfStep = it.contentOfStep,
                  recipeId = recipeNewId,
                  imageUrl = it.imageUrl
              ))

        }
        currentRecipe.value = null
        currentStep.value = null
        currentSteps.value = null

    }
}