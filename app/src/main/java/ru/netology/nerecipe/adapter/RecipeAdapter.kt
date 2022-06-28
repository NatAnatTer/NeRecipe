package ru.netology.nerecipe.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nerecipe.R
import ru.netology.nerecipe.adapter.helper.ItemTouchHelperAdapter
import ru.netology.nerecipe.adapter.helper.ItemTouchHelperViewHolder
import ru.netology.nerecipe.databinding.RecipeListItemBinding
import ru.netology.nerecipe.dto.RecipeWithInfo
import java.util.*


internal class RecipeAdapter(
    private val interactionListener: RecipeInteractionListener

) : ListAdapter<RecipeWithInfo, RecipeAdapter.ViewHolder>(DiffCallBack), ItemTouchHelperAdapter {

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
    ) : RecyclerView.ViewHolder(binding.root), ItemTouchHelperViewHolder {


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
            binding.menu.setOnClickListener { popupMenu.show() }
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

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
    private object DiffCallBack : DiffUtil.ItemCallback<RecipeWithInfo>() {
        override fun areItemsTheSame(oldItem: RecipeWithInfo, newItem: RecipeWithInfo) =
            oldItem.recipe.recipeId == newItem.recipe.recipeId

        override fun areContentsTheSame(oldItem: RecipeWithInfo, newItem: RecipeWithInfo) =
            oldItem == newItem

    }

//    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun onItemDismiss(position: Int) {
//        TODO("Not yet implemented")
//    }

    override fun onItemDismiss(position: Int) {
        currentList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
      //  Collections.swap(currentList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun getItemCount(): Int {
        return currentList.size
    }


    //    fun onItemDismiss(position: Int) {
//        val list = currentList.toMutableList()
//        list.removeAt(position)
//        notifyItemRemoved(position)
//    }
    fun moveItemInRecyclerViewList(from: Int, to: Int) {

        val list = currentList.toMutableList()
        val fromLocation = list[from]
        list.removeAt(from)
        if (to < from) {
            list.add(to + 1 , fromLocation)
        } else {
            list.add(to - 1, fromLocation)
        }
        submitList(list)
        notifyItemMoved(from, to);
    }
}
