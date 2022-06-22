package ru.netology.nerecipe.adapter

import ru.netology.nerecipe.dto.Recipe


interface RecipeInteractionListener {
    fun onFavoriteClicked(recipe: Recipe)
    fun onRemoveClicked(recipe: Recipe)
    fun onEditClicked(recipe: Recipe)
    fun onShowRecipeClicked(recipe: Recipe)
}
