package ru.netology.nerecipe.recipeWievModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nerecipe.data.RecipeRepositoryImpl
import ru.netology.nerecipe.db.AppDb
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.util.SingleLiveEvent
import ru.netology.nmedia.adapter.RecipeInteractionListener
import ru.netology.nmedia.data.RecipeRepository


class RecipeViewModel(application: Application) : AndroidViewModel(application),
    RecipeInteractionListener {
    private val repository: RecipeRepository = RecipeRepositoryImpl(
        dao = AppDb.getInstance(context = application).recipeDao
    )
    val data by repository::data
    private val currentRecipe = MutableLiveData<Recipe?>(null)

    //val sharePostContent = SingleLiveEvent<String>()
   // val videoLinkPlay = SingleLiveEvent<String>()
    private val navigateToRecipeContentScreenEvent = SingleLiveEvent<String>()
    private val navigateToShowRecipe = SingleLiveEvent<Long>()


    override fun onFavoriteClicked(recipe: Recipe) = repository.favorite(recipe.recipeId)
    override fun onRemoveClicked(recipe: Recipe) = repository.delete(recipe.recipeId)
    override fun onEditClicked(recipe: Recipe){}
    override fun onShowRecipeClicked(recipe: Recipe){
        navigateToShowRecipe.value = recipe.recipeId
    }

        fun onAddButtonClicked() {
        navigateToRecipeContentScreenEvent.call()
    }




//    override fun onRemoveClicked(post: Post) = repository.delete(post.id)
//    override fun onEditClicked(post: Post) {
//        currentPost.value = post
//        navigateToPostContentScreenEvent.value = post.content
//    }
//
//    override fun onPlayVideoClicked(post: Post) {
//        videoLinkPlay.value = post.urlVideo!!
//    }
//
//
//    fun onSaveButtonClicked(content: String) {
//        if (content.isBlank()) return
//
//        val newPost = currentPost.value?.copy(
//            content = content
//        ) ?: Post(
//            id = PostRepository.NEW_POST_ID,
//            author = "Me",
//            content = content,
//            published = "Now",
//            avatar = R.drawable.ic_new_avatar_48,
//            videoAttachmentCover = null,
//            videoAttachmentHeader = null,
//            urlVideo = null
//        )
//        repository.save(newPost)
//        currentPost.value = null
//    }
}