package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface RecipeRepository {

    val data: LiveData<List<Post>>

    fun like(postId: Long)

    fun repost(postId: Long)

    fun delete(postId: Long)

    fun save(post: Post)

    fun getPost(postId: Long): Post?

    companion object {
        const val NEW_POST_ID = 0L
    }
}