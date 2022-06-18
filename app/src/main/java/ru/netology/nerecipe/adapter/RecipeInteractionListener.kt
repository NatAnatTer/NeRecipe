package ru.netology.nmedia.adapter

import ru.netology.nerecipe.dto.Recipe


interface RecipeInteractionListener {
    fun onFavoriteClicked(recipe: Recipe)
    fun onRemoveClicked(recipe: Recipe)
    fun onEditClicked(recipe: Recipe)
    fun onShowRecipeClicked(recipe: Recipe)
}


//  fun onRepostClicked(post: Post)

//  fun onPlayVideoClicked(post: Post)