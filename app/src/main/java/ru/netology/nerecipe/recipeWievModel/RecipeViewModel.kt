package ru.netology.nerecipe.recipeWievModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nerecipe.data.RecipeRepositoryImpl
import ru.netology.nerecipe.db.AppDb
import ru.netology.nerecipe.dto.CategoryOfRecipe
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.dto.Steps
import ru.netology.nerecipe.util.SingleLiveEvent
import ru.netology.nmedia.adapter.RecipeInteractionListener
import ru.netology.nerecipe.data.RecipeRepository
import ru.netology.nerecipe.data.RecipeRepository.Companion.NEW_RECIPE_ID


class RecipeViewModel(application: Application) : AndroidViewModel(application),
    RecipeInteractionListener {
    private val repository: RecipeRepository = RecipeRepositoryImpl(
        dao = AppDb.getInstance(context = application).recipeDao,
           //     daoRecipeSteps = AppDb.getInstance(context = application).recipeStepDao
    )
    val data by repository::data
    private val currentRecipe = MutableLiveData<Recipe?>(null)

    val navigateToRecipeChangeContentScreenEvent = SingleLiveEvent<Long>()
    val navigateToShowRecipe = SingleLiveEvent<Long>()


    override fun onFavoriteClicked(recipe: Recipe) {
        repository.favoritesByMe(recipe.recipeId)
    } //= repository.favorite(recipe.recipeId)

    override fun onRemoveClicked(recipe: Recipe) = repository.delete(recipe.recipeId)
    override fun onEditClicked(recipe: Recipe) {
        currentRecipe.value = recipe
      //  navigateToRecipeContentScreenEvent.value = recipe.recipeId
    }

    override fun onShowRecipeClicked(recipe: Recipe) {
        navigateToShowRecipe.value = recipe.recipeId
    }

//    fun onAddButtonClicked() {
//        navigateToRecipeContentScreenEvent.call()
//    }

    fun createCategory(category: List<CategoryOfRecipe>) {
        repository.createCategory(category)
    }

    fun getStepsByRecipeId(recipeId: Long): List<Steps> = repository.getStepsByRecipeId(recipeId)

//    fun createUser(user: User){
//        repository.createUser(user)
//    }
//    fun getCurrentUser(userName: String) = repository.getCurrentUser(userName)


    fun onSaveButtonClicked(
        recipe: Recipe,
        steps: List<Steps>
    ) {
        if (steps.isEmpty()) return

        val newRecipe = currentRecipe.value?.copy(
            recipeName = recipe.recipeName,
            categoryId = recipe.categoryId,

            ) ?: Recipe(
            recipeId = NEW_RECIPE_ID,
            recipeName = recipe.recipeName,
            categoryId = recipe.categoryId,
            authorName = recipe.authorName,
            isFavorites = false
        )
         val recipeNewId = repository.save(newRecipe)
        steps.forEach {

              repository.saveSteps(Steps(
                  stepId = 0L,
                  numberOfStep = it.numberOfStep,
                  contentOfStep = it.contentOfStep,
                  recipeId = recipeNewId,
                  imageUrl = it.imageUrl
              ))

        }
        currentRecipe.value = null

    }
}