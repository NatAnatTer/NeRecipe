package ru.netology.nerecipe.adapter

import ru.netology.nerecipe.dto.CategoryOfRecipe
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.dto.Steps


interface RecipeInteractionListener {
    fun onFavoriteClicked(recipe: Recipe)
    fun onRemoveClicked(recipe: Recipe)
    fun onEditClicked(recipe: Recipe)
    fun onShowRecipeClicked(recipe: Recipe)
    fun onStepClicked(step: Steps)
    fun onAddButtonClicked()
  //  fun onFilterCheckBoxClicked(categoryRecipe: CategoryOfRecipe)
}
