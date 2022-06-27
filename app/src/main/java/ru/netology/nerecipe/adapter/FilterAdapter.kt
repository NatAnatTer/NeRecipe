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
    private val flagInput: Boolean
) : ListAdapter<CategoryOfRecipe, FilterAdapter.ViewHolderCategory>(DiffCallBack) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderCategory {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CheckBoxListItemBinding.inflate(inflater, parent, false)
        return ViewHolderCategory(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolderCategory, position: Int) {
        holder.bind(getItem(position), flagInput)
    }

    class ViewHolderCategory(
        private val binding: CheckBoxListItemBinding,
        private val listener: RecipeInteractionListener,
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var categoryOfRecipe: CategoryOfRecipe
        fun bind(categoryOfRecipe: CategoryOfRecipe, flagInput: Boolean) {
            this.categoryOfRecipe = categoryOfRecipe
            binding.cbSelectCategory.isChecked = flagInput
            binding.categoryToSelect.text = categoryOfRecipe.categoryName

            binding.categoryToSelect.setOnClickListener {
                val flag = binding.cbSelectCategory.isChecked
                listener.onFilterCheckBoxClicked(
                    categoryOfRecipe,
                    flag
                )
                binding.cbSelectCategory.isChecked = flag
            }

            binding.cbSelectCategory.setOnClickListener {
                val flag = binding.cbSelectCategory.isChecked
                listener.onFilterCheckBoxClicked(
                    categoryOfRecipe,
                    flag
                )
                binding.cbSelectCategory.isChecked = flag
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


//
//internal class FilterAdapter(
//  //  private var checkedCategory: List<CategoryOfRecipe>?
//    private val interactionListener: RecipeInteractionListener
//) : ListAdapter<CategoryOfRecipe, FilterAdapter.ViewHolderCategory>(DiffCallBack) {
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): ViewHolderCategory {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = CheckBoxListItemBinding.inflate(inflater, parent, false)
//        return ViewHolderCategory(binding, interactionListener)//, checkedCategory)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolderCategory, position: Int) {
//        holder.bind(getItem(position))
//    }
//
//
//
//    class ViewHolderCategory(
//        private val binding: CheckBoxListItemBinding,
//        private val listener: RecipeInteractionListener
//        //  checkedCategory: List<CategoryOfRecipe>?
//    ) : RecyclerView.ViewHolder(binding.root) {
//
//        private var checkedCategoryNewList = listener.getCheckedCategory() //checkedCategory
//
//        private lateinit var categoryOfRecipe: CategoryOfRecipe
//        fun bind(categoryOfRecipe: CategoryOfRecipe) {
//            this.categoryOfRecipe = categoryOfRecipe
//
//
//
//                binding.cbSelectCategory.isChecked = false
//                    checkedCategoryNewList?.contains(categoryOfRecipe) ?: false
//
//                binding.categoryToSelect.text = categoryOfRecipe.categoryName
//
//
//
//            binding.categoryToSelect.setOnClickListener {
//                binding.cbSelectCategory.isChecked =
//                    when {
//
//                        checkedCategoryNewList.isNullOrEmpty() -> {
//                            checkedCategoryNewList =
//                                checkedCategoryNewList?.plus(categoryOfRecipe) ?: listOf(
//                                    categoryOfRecipe
//                                )
//                            true
//                        }
//                        checkedCategoryNewList!!.contains(categoryOfRecipe) -> {
//                            checkedCategoryNewList =
//                                checkedCategoryNewList!!.filter { it.categoryId != categoryOfRecipe.categoryId }
//                            false
//                        }
//                        !checkedCategoryNewList!!.contains(categoryOfRecipe) -> {
//                            checkedCategoryNewList =
//                                checkedCategoryNewList?.plus(categoryOfRecipe) ?: listOf(
//                                    categoryOfRecipe
//                                )
//                            true
//                        }
//                        else -> false
//                    }
//
//
//            }
//
//
//            binding.cbSelectCategory.setOnClickListener {
//                binding.cbSelectCategory.isChecked =
//                    when {
//                        checkedCategoryNewList.isNullOrEmpty() -> {
//                            checkedCategoryNewList =
//                                checkedCategoryNewList?.plus(categoryOfRecipe) ?: listOf(
//                                    categoryOfRecipe
//                                )
//                            true
//                        }
//                        checkedCategoryNewList!!.contains(categoryOfRecipe) -> {
//                            checkedCategoryNewList =
//                                checkedCategoryNewList!!.filter { it.categoryId != categoryOfRecipe.categoryId }
//                            false
//                        }
//                        !checkedCategoryNewList!!.contains(categoryOfRecipe) -> {
//                            checkedCategoryNewList =
//                                checkedCategoryNewList?.plus(categoryOfRecipe) ?: listOf(
//                                    categoryOfRecipe
//                                )
//                            true
//                        }
//                        else -> false
//                    }
//            }
//        }
//    }
////    fun setCheckedCategoryList(categoryList: List<CategoryOfRecipe>?): List<CategoryOfRecipe>? {
////        checkedCategory = categoryList
////        return checkedCategory
////    }
//  }
//
//private object DiffCallBack : DiffUtil.ItemCallback<CategoryOfRecipe>() {
//    override fun areItemsTheSame(oldItem: CategoryOfRecipe, newItem: CategoryOfRecipe) =
//        oldItem.categoryId == newItem.categoryId
//
//    override fun areContentsTheSame(
//        oldItem: CategoryOfRecipe,
//        newItem: CategoryOfRecipe
//    ) =
//        oldItem == newItem
//}







