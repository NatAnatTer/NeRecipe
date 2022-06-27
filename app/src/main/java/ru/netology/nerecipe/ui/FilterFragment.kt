package ru.netology.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import ru.netology.nerecipe.R
import ru.netology.nerecipe.adapter.FilterAdapter
import ru.netology.nerecipe.databinding.FilterFragmentBinding
import ru.netology.nerecipe.dto.CategoryOfRecipe
import ru.netology.nerecipe.recipeWievModel.RecipeViewModel

class FilterFragment : Fragment() {

    private val viewModel by viewModels<RecipeViewModel>(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FilterFragmentBinding.inflate(inflater, container, false)
        .also { binding ->

            val allCategory = viewModel.getAllCategory()
            var checkedCategory: List<CategoryOfRecipe>? =
                null //TODO класть данные из json from feedFragment

            if (checkedCategory != null) {
                binding.cbSelectAll.isChecked = allCategory.size == checkedCategory.size + 1
            } else {
                binding.cbSelectAll.isChecked = true
                checkedCategory = allCategory
            }


            val adapter = FilterAdapter(viewModel)
            binding.categoryListRecyclerView.adapter = adapter
            viewModel.allCategoryOfRecipe.observe(viewLifecycleOwner) {
                adapter.submitList(it)
                //  adapter.setCheckedCategoryList(checkedCategory)
            }
            binding.bottomNavigationApplyFilter.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.back -> {
                        findNavController().popBackStack()
                        true
                    }
                    R.id.apply_filter -> {
                        //TODO
                        true
                    }
                    else -> false
                }
            }

        }.root

    override fun onResume() {
        super.onResume()

        val mapper = ObjectMapper().registerKotlinModule()

        setFragmentResultListener(requestKey = FeedFragment.REQUEST_KEY) { requestKey, bundle ->
            if (requestKey != FeedFragment.REQUEST_KEY) return@setFragmentResultListener
            val newCategoryListString =
                bundle.getString(FeedFragment.RESULT_KEY)
                    ?: return@setFragmentResultListener
            val newCategoryList =
                mapper.readValue(newCategoryListString, CategoryOfRecipe::class.java)

            viewModel.setFilteredCategory(listOf(newCategoryList))
        }

    }

    companion object {
        const val RESULT_FILTER_KEY = "CategoryFilter"
        const val REQUEST_KEY = "requestKey"

    }
}


//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        viewModel.navigateToRecipeChangeContentScreenEvent.observe(this) { recipeId ->
//            val direction =
//                recipeId?.let { RecipeShowDetailFragmentDirections.toChangeContentFragmet(it) }
//            if (direction != null) {
//                findNavController().navigate(direction)
//            }
//
//        }
//
//
//    }
//}