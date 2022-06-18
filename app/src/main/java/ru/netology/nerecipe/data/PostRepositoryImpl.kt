package ru.netology.nerecipe.data

import androidx.lifecycle.map
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.db.toEntity
import ru.netology.nmedia.db.toModel
import ru.netology.nmedia.dto.Post

class PostRepositoryImpl(
    private val dao: PostDao
) : PostRepository {

    override val data = dao.getAll().map { entities ->
        entities.map {
            it.toModel()
        }
    }

    override fun like(postId: Long) = dao.likeByMe(postId)


    override fun delete(postId: Long) {
        dao.removeById(postId)
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) dao.insert(post.toEntity())
        else dao.updateContentById(post.id, post.content)
    }

    override fun getPost(postId: Long) = dao.getById(postId).toModel()

    override fun repost(postId: Long) =
        dao.repost(postId)


}