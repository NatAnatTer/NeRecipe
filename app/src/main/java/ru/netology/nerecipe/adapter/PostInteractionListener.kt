package ru.netology.nmedia.adapter

import ru.netology.nmedia.dto.Post

interface PostInteractionListener {
    fun onLikeClicked(post: Post)
    fun onRepostClicked(post: Post)
    fun onRemoveClicked(post: Post)
    fun onEditClicked(post: Post)
    fun onPlayVideoClicked(post: Post)
    fun onShowPostClicked(post: Post)
}

//interface PostShowDetailInteractionListener {
//    fun onLikeClicked()
//    fun onRepostClicked()
//    fun onRemoveClicked()
//    fun onEditClicked()
//    fun onPlayVideoClicked()
//
//}