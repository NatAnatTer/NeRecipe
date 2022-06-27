package ru.netology.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
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
            var flag = true
            binding.cbSelectAll.isChecked = flag
            val adapter = FilterAdapter(viewModel, flag)
            binding.categoryListRecyclerView.adapter = adapter
            viewModel.allCategoryOfRecipe.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }

            binding.cbSelectAll.setOnClickListener {
                flag = binding.cbSelectAll.isChecked
                val adapter = FilterAdapter(viewModel, flag)
                binding.categoryListRecyclerView.adapter = adapter
                viewModel.allCategoryOfRecipe.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                }
                viewModel.checkedAllCategory(flag)
            }



            binding.bottomNavigationApplyFilter.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.back -> {
                        findNavController().popBackStack()
                        true
                    }
                    R.id.apply_filter -> {
                        viewModel.getCheckedCategory()?.let { filteredList(it) }
                        //TODO
                        true
                    }
                    else -> false
                }
            }

        }.root

//    override fun onResume() {
//        super.onResume()
//
//        val mapper = ObjectMapper().registerKotlinModule()
//
//        setFragmentResultListener(requestKey = FeedFragment.REQUEST_KEY) { requestKey, bundle ->
//            if (requestKey != FeedFragment.REQUEST_KEY) return@setFragmentResultListener
//            val newCategoryListString =
//                bundle.getString(FeedFragment.RESULT_KEY)
//                    ?: return@setFragmentResultListener
//            val newCategoryList =
//                mapper.readValue(newCategoryListString, CategoryOfRecipe::class.java)
//
//            viewModel.setCheckedCategory(listOf(newCategoryList))
//        }
//
//    }

    private fun filteredList(categoryToFilter: List<CategoryOfRecipe>) {
        val resultBundle = Bundle(2)
        val contentNew = ObjectMapper().writeValueAsString(categoryToFilter)
        resultBundle.putString(RESULT_KEY, contentNew)
        setFragmentResult(REQUEST_KEY, resultBundle)

        findNavController().popBackStack()
    }

    companion object {
        const val RESULT_KEY = "CategoryFilter"
        const val REQUEST_KEY = "requestKeyFilter"

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