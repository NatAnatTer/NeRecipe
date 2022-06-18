package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.ScrollView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostListItemBinding
import java.text.DecimalFormat


internal class PostsAdapter(
    private val interactionListener: PostInteractionListener
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

     class ViewHolder(
        private val binding: PostListItemBinding,
        private val listener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post
        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.menu).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.like.setOnClickListener { listener.onLikeClicked(post) }
            binding.reposts.setOnClickListener { listener.onRepostClicked(post) }
            binding.menu.setOnClickListener { popupMenu.show() }
            binding.videoPreview.setOnClickListener { listener.onPlayVideoClicked(post) }
            binding.videoPreviewButtonPlay.setOnClickListener { listener.onPlayVideoClicked(post) }
            binding.date.setOnClickListener { listener.onShowPostClicked(post) }
            binding.postBody.setOnClickListener { listener.onShowPostClicked(post) }
        }


        fun bind(post: Post) {
            this.post = post
            with(binding) {
                authorName.text = post.author
                date.text = post.published
                post.content.also { postBody.text = it }
                like.text = getTextViewCount(post.likes)
                like.isChecked = post.likedByMe
                usersViews.text = getTextViewCount(post.views)
                reposts.text = getTextViewCount(post.reposts)
                avatar.setImageResource(post.avatar)
                if (post.videoAttachmentCover != null) {
                    videoPreview.setImageResource(post.videoAttachmentCover.toInt())
                    videoTitle.text = post.videoAttachmentHeader
                    videoPreview.visibility = View.VISIBLE
                    videoTitle.visibility = View.VISIBLE
                    videoPreviewButtonPlay.visibility = View.VISIBLE
                } else {
                    videoPreview.visibility = View.GONE
                    videoTitle.visibility = View.GONE
                    videoPreviewButtonPlay.visibility = View.GONE
                }

            }
        }

    }

    private object DiffCallBack : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem

    }
}

fun getTextViewCount(count: Int): String {
    val df1 = DecimalFormat("#.#")
    return when (count) {
        in 0..999 -> count.toString()
        in 1000..1099 -> (count / 1000).toString() + "K"
        in 1100..9999 -> (df1.format((count / 100).toDouble() / 10.0)).toString() + "K"
        in 10_000..999_999 -> (count / 1000).toString() + "K"
        in 1_000_000..1_099_999 -> (count / 1_000_000).toString() + "M"
        in 1_100_000..999_999_999 -> (df1.format((count / 100_000).toDouble() / 10.0)).toString() + "M"
        else -> "1B"
    }
}

