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


            val adapter = FilterAdapter(checkedCategory)
            binding.categoryListRecyclerView.adapter = adapter
            viewModel.data.observe(viewLifecycleOwner) {
                val categoryList = viewModel.getAllCategory()
                adapter.submitList(categoryList)
                adapter.setCheckedCategoryList(checkedCategory)
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
            if (requestKey != RecipeChangeContentFragment.REQUEST_KEY) return@setFragmentResultListener
            val newRecipeContentString =
                bundle.getString(RecipeChangeContentFragment.RESULT_KEY)
                    ?: return@setFragmentResultListener
            val newRecipeContent =
                mapper.readValue(newRecipeContentString, RecipeWithInfo::class.java)

            viewModel.onSaveButtonClicked(newRecipeContent)
        }

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