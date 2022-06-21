package ru.netology.nerecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nerecipe.R
import ru.netology.nerecipe.databinding.RecipeListItemBinding
import ru.netology.nerecipe.databinding.RecipeStepsBinding
import ru.netology.nerecipe.dto.RecipeWithInfo
import ru.netology.nerecipe.dto.Steps
import ru.netology.nmedia.adapter.RecipeInteractionListener


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
            binding.favorites.setOnClickListener { listener.onFavoriteClicked(recipe.recipe) }
//            binding.reposts.setOnClickListener { listener.onRepostClicked(post) }
            binding.menu.setOnClickListener { popupMenu.show() }
//            binding.videoPreview.setOnClickListener { listener.onPlayVideoClicked(post) }
//            binding.videoPreviewButtonPlay.setOnClickListener { listener.onPlayVideoClicked(post) }
            binding.recipeName.setOnClickListener { listener.onShowRecipeClicked(recipe.recipe) }
            binding.category.setOnClickListener { listener.onShowRecipeClicked(recipe.recipe) }
            binding.author.setOnClickListener { listener.onShowRecipeClicked(recipe.recipe) }
            binding.authorName.setOnClickListener { listener.onShowRecipeClicked(recipe.recipe) }

        }

        fun bind(recipe: RecipeWithInfo) {
            this.recipe = recipe
            with(binding) {
                recipeName.text = recipe.recipe.recipeName
                category.text = recipe.category.categoryName
                authorName.text = recipe.recipe.authorName
                favorites.isChecked = recipe.recipe.isFavorites
            }

        }


    }

//    class ViewHolderSteps(
//        private val binding: RecipeStepsBinding
//    ) : RecyclerView.ViewHolder(binding.root) {
//
//        private lateinit var steps: Steps
//
//
//        fun bind(recipe: RecipeWithInfo) {
//            this.steps = recipe.steps.get(steps)
//            with(binding) {
//                nameOfStep.text = recipe.steps.
//                recipeName.text = recipe.recipe.recipeName
//                category.text = recipe.category.categoryName
//                authorName.text = recipe.recipe.authorName
//                favorites.isChecked = recipe.recipe.isFavorites
//            }
//
//        }
//
//
//    }

    private object DiffCallBack : DiffUtil.ItemCallback<RecipeWithInfo>() {
        override fun areItemsTheSame(oldItem: RecipeWithInfo, newItem: RecipeWithInfo) =
            oldItem.recipe.recipeId == newItem.recipe.recipeId

        override fun areContentsTheSame(oldItem: RecipeWithInfo, newItem: RecipeWithInfo) =
            oldItem == newItem

    }
}



