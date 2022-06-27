package ru.netology.nerecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nerecipe.databinding.CheckBoxListItemBinding
import ru.netology.nerecipe.dto.CategoryOfRecipe


internal class FilterAdapter(
    private val interactionListener: RecipeInteractionListener,
    private val checkedCategory: List<CategoryOfRecipe>
) :
    ListAdapter<CategoryOfRecipe, FilterAdapter.ViewHolderCategory>(DiffCallBack) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterAdapter.ViewHolderCategory {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CheckBoxListItemBinding.inflate(inflater, parent, false)
        return FilterAdapter.ViewHolderCategory(binding, interactionListener, checkedCategory)
    }

    override fun onBindViewHolder(holder: FilterAdapter.ViewHolderCategory, position: Int) {
        holder.bind(getItem(position))
    }
    class ViewHolderCategory(
        private val binding: CheckBoxListItemBinding,
        private val listener: RecipeInteractionListener,
        private val checkedCategory: List<CategoryOfRecipe>?
    ) : RecyclerView.ViewHolder(binding.root) {

        private var checkedCategoryNewList = checkedCategory
        private lateinit var categoryOfRecipe: CategoryOfRecipe
        fun bind(categoryOfRecipe: CategoryOfRecipe) {
            this.categoryOfRecipe = categoryOfRecipe

            binding.categoryToSelect.text = categoryOfRecipe.categoryName

            binding.cbSelectCategory.isChecked =
                checkedCategoryNewList?.contains(categoryOfRecipe) ?: false



            binding.categoryToSelect.setOnClickListener {
                binding.cbSelectCategory.isChecked =
                    when {

                        checkedCategoryNewList.isNullOrEmpty() -> {
                            checkedCategoryNewList =
                                checkedCategoryNewList?.plus(categoryOfRecipe) ?: listOf(
                                    categoryOfRecipe
                                )
                            true
                        }
                        checkedCategoryNewList!!.contains(categoryOfRecipe) -> {
                            checkedCategoryNewList =
                                checkedCategoryNewList!!.filter { it.categoryId != categoryOfRecipe.categoryId }
                            false
                        }
                        !checkedCategoryNewList!!.contains(categoryOfRecipe) -> {
                            checkedCategoryNewList =
                                checkedCategoryNewList?.plus(categoryOfRecipe) ?: listOf(
                                    categoryOfRecipe
                                )
                            true
                        }

                        else -> false
                    }


            }


            binding.cbSelectCategory.setOnClickListener {
                binding.cbSelectCategory.isChecked =
                    when {

                        checkedCategoryNewList.isNullOrEmpty() -> {
                            checkedCategoryNewList =
                                checkedCategoryNewList?.plus(categoryOfRecipe) ?: listOf(
                                    categoryOfRecipe
                                )
                            true
                        }
                        checkedCategoryNewList!!.contains(categoryOfRecipe) -> {
                            checkedCategoryNewList =
                                checkedCategoryNewList!!.filter { it.categoryId != categoryOfRecipe.categoryId }
                            false
                        }
                        !checkedCategoryNewList!!.contains(categoryOfRecipe) -> {
                            checkedCategoryNewList =
                                checkedCategoryNewList?.plus(categoryOfRecipe) ?: listOf(
                                    categoryOfRecipe
                                )
                            true
                        }

                        else -> false
                    }
            }


        }
    }
}

private object DiffCallBack : DiffUtil.ItemCallback<CategoryOfRecipe>() {
    override fun areItemsTheSame(oldItem: CategoryOfRecipe, newItem: CategoryOfRecipe) =
        oldItem.categoryId == newItem.categoryId

    override fun areContentsTheSame(
        oldItem: CategoryOfRecipe,
        newItem: CategoryOfRecipe
    ) =
        oldItem == newItem

}





