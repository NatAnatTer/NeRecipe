package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nerecipe.R
import ru.netology.nerecipe.databinding.RecipeListItemBinding
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.dto.RecipeWithInfo
import java.text.DecimalFormat


internal class RecipeAdapter(

    private val interactionListener: RecipeInteractionListener
) : ListAdapter<RecipeWithInfo, RecipeAdapter.ViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: RecipeListItemBinding,
        private val listener: RecipeInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var recipe: RecipeWithInfo
        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.menu).apply {
                inflate(R.menu.options_recipe)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(recipe.recipe)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(recipe.recipe)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
//            binding.like.setOnClickListener { listener.onLikeClicked(post) }
//            binding.reposts.setOnClickListener { listener.onRepostClicked(post) }
//            binding.menu.setOnClickListener { popupMenu.show() }
//            binding.videoPreview.setOnClickListener { listener.onPlayVideoClicked(post) }
//            binding.videoPreviewButtonPlay.setOnClickListener { listener.onPlayVideoClicked(post) }
//            binding.date.setOnClickListener { listener.onShowPostClicked(post) }
//            binding.postBody.setOnClickListener { listener.onShowPostClicked(post) }
        }

        fun bind(recipe: RecipeWithInfo) {
            this.recipe = recipe
            with(binding) {
                recipeName.text = recipe.recipe.recipeName
                category.text = recipe.category.categoryName
                authorName.text = recipe.user.userName
                //  favorites.isChecked = TODO()
            }

        }
//        fun bind(post: Post) {
//            this.post = post
//            with(binding) {
//                authorName.text = post.author
//                date.text = post.published
//                post.content.also { postBody.text = it }
//                like.text = getTextViewCount(post.likes)
//                like.isChecked = post.likedByMe
//                usersViews.text = getTextViewCount(post.views)
//                reposts.text = getTextViewCount(post.reposts)
//                avatar.setImageResource(post.avatar)
//                if (post.videoAttachmentCover != null) {
//                    videoPreview.setImageResource(post.videoAttachmentCover.toInt())
//                    videoTitle.text = post.videoAttachmentHeader
//                    videoPreview.visibility = View.VISIBLE
//                    videoTitle.visibility = View.VISIBLE
//                    videoPreviewButtonPlay.visibility = View.VISIBLE
//                } else {
//                    videoPreview.visibility = View.GONE
//                    videoTitle.visibility = View.GONE
//                    videoPreviewButtonPlay.visibility = View.GONE
//                }
//
//            }
//        }

    }

    private object DiffCallBack : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem.recipeId == newItem.recipeId

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) =
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

