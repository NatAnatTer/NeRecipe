package ru.netology.nerecipe.recipeWievModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class ViewModel {
}


class PostViewModel(application: Application) : AndroidViewModel(application),
    PostInteractionListener {
    private val repository: PostRepository = PostRepositoryImpl(
        dao = AppDb.getInstance(context = application).postDao
    )
    val data by repository::data
    private val currentPost = MutableLiveData<Post?>(null)

    val sharePostContent = SingleLiveEvent<String>()
    val videoLinkPlay = SingleLiveEvent<String>()
    val navigateToPostContentScreenEvent = SingleLiveEvent<String>()
    val navigateToShowPost = SingleLiveEvent<Long>()

    fun onAddButtonClicked() {
        navigateToPostContentScreenEvent.call()
    }

    override fun onShowPostClicked(post: Post) {
        //currentPost.value = post
        navigateToShowPost.value = post.id
    }

    fun getPost(postId: Long) = repository.getPost(postId)

    override fun onLikeClicked(post: Post) = repository.like(post.id)
    override fun onRepostClicked(post: Post) {
        sharePostContent.value = post.content
        repository.repost(post.id)
    }

    override fun onRemoveClicked(post: Post) = repository.delete(post.id)
    override fun onEditClicked(post: Post) {
        currentPost.value = post
        navigateToPostContentScreenEvent.value = post.content
    }

    override fun onPlayVideoClicked(post: Post) {
        videoLinkPlay.value = post.urlVideo!!
    }


    fun onSaveButtonClicked(content: String) {
        if (content.isBlank()) return

        val newPost = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Me",
            content = content,
            published = "Now",
            avatar = R.drawable.ic_new_avatar_48,
            videoAttachmentCover = null,
            videoAttachmentHeader = null,
            urlVideo = null
        )
        repository.save(newPost)
        currentPost.value = null
    }
}