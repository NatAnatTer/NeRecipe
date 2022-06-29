package ru.netology.nerecipe.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import ru.netology.nerecipe.R
import ru.netology.nerecipe.adapter.RecipeAdapter
import ru.netology.nerecipe.adapter.helper.SimpleItemTouchHelperCallback
import ru.netology.nerecipe.databinding.FeedFragmentBinding
import ru.netology.nerecipe.dto.CategoryOfRecipe
import ru.netology.nerecipe.dto.RecipeWithInfo
import ru.netology.nerecipe.recipeWievModel.RecipeViewModel


class FeedFragment : Fragment() {

    private val viewModel by viewModels<RecipeViewModel>(ownerProducer = ::requireParentFragment)

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val categoryList = listOf(
            CategoryOfRecipe(
                categoryId = 0L,
                categoryName = "Не выбрано"
            ),
            CategoryOfRecipe(
                categoryId = 0L,
                categoryName = "Европейская"
            ),
            CategoryOfRecipe(
                categoryId = 0L,
                categoryName = "Азиатская"
            ),
            CategoryOfRecipe(
                categoryId = 0L,
                categoryName = "Паназиатская"
            ),
            CategoryOfRecipe(
                categoryId = 0L,
                categoryName = "Восточная"
            ),
            CategoryOfRecipe(
                categoryId = 0L,
                categoryName = "Американская"
            ),
            CategoryOfRecipe(
                categoryId = 0L,
                categoryName = "Русская"
            ),
            CategoryOfRecipe(
                categoryId = 0L,
                categoryName = "Средиземноморская"
            )
        )
        viewModel.createCategory(categoryList)

        viewModel.navigateToRecipeChangeContentScreenEvent.observe(this) { idRecipe ->
            val direction = idRecipe?.let { FeedFragmentDirections.toChangeContentFragment(it) }
                ?: FeedFragmentDirections.toChangeContentFragment(0L)
            findNavController().navigate(direction)
        }
        viewModel.navigateToShowRecipe.observe(this) { recipeId ->
            val direction = recipeId?.let { FeedFragmentDirections.toRecipeShowDetailFragment(it) }
            if (direction != null) {
                findNavController().navigate(direction)
            }
        }
        viewModel.navigateToFilter.observe(this) {
            val direction = FeedFragmentDirections.toFilterFragment()
            findNavController().navigate(direction)
        }
    }

    override fun onResume() {
        super.onResume()

        val mapper = ObjectMapper().registerKotlinModule()
        setFragmentResultListener(requestKey = RecipeChangeContentFragment.REQUEST_KEY) { requestKey, bundle ->
            if (requestKey != RecipeChangeContentFragment.REQUEST_KEY) return@setFragmentResultListener
            val newRecipeContentString =
                bundle.getString(RecipeChangeContentFragment.RESULT_KEY)
                    ?: return@setFragmentResultListener
            val newRecipeContent =
                mapper.readValue(newRecipeContentString, RecipeWithInfo::class.java)
            viewModel.onSaveButtonClicked(newRecipeContent)
        }

        val mapperFilter = ObjectMapper().registerKotlinModule()
        setFragmentResultListener(requestKey = FilterFragment.REQUEST_KEY) { requestKey, bundle ->
            if (requestKey != FilterFragment.REQUEST_KEY) return@setFragmentResultListener
            val newCategoryListString =
                bundle.getString(FilterFragment.RESULT_KEY)
                    ?: return@setFragmentResultListener
            val newCategoryList =
                mapperFilter.readValue<List<CategoryOfRecipe>>(newCategoryListString)

            viewModel.onFilterClicked(newCategoryList)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FeedFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        val adapter = RecipeAdapter(viewModel)

        binding.recipeListRecyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { recipe ->
            viewModel.filteredListOfRecipe.observe(viewLifecycleOwner) { recipeFiltered ->
                if (recipeFiltered.isNullOrEmpty()) {
                    if (!recipe.isNullOrEmpty()) binding.emptyStatesImage.visibility =
                        View.GONE else binding.emptyStatesImage.visibility = View.VISIBLE
                    adapter.submitList(recipe)
                } else {
                    if (!recipe.isNullOrEmpty()) binding.emptyStatesImage.visibility =
                        View.GONE else binding.emptyStatesImage.visibility = View.VISIBLE
                    adapter.submitList(recipeFiltered)

                }
            }
        }
        binding.searchBar.visibility = View.GONE


        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter)
        val mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper.attachToRecyclerView(binding.recipeListRecyclerView)

        fun setUpSearchView() {
            binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrEmpty()) {
                        adapter.submitList(viewModel.data.value)
                        return true
                    }
                    var ls = adapter.currentList
                    ls = ls.filter { e ->
                        e.recipe.recipeName.lowercase().contains(newText.lowercase())
                    }
                    adapter.submitList(ls)
                    return true
                }
            })
        }

        fun onFavoriteClicked() {
            var newListRecipe: List<RecipeWithInfo>?
            viewModel.data.observe(viewLifecycleOwner) {

                newListRecipe = viewModel.data.value?.filter {
                    it.recipe.isFavorites
                }
                adapter.submitList(newListRecipe)
                if (newListRecipe.isNullOrEmpty()) binding.emptyStatesImage.visibility =
                    View.VISIBLE else binding.emptyStatesImage.visibility = View.GONE
            }
        }

        fun onRecipeListClicked() {
            viewModel.data.observe(viewLifecycleOwner) {
                if (viewModel.data.value.isNullOrEmpty()) binding.emptyStatesImage.visibility =
                    View.VISIBLE else binding.emptyStatesImage.visibility = View.GONE
                adapter.submitList(viewModel.data.value)

            }
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.recipe_list -> {
                    onRecipeListClicked()
                    true
                }
                R.id.favorites_list -> {
                    onFavoriteClicked()
                    true
                }
                R.id.add_recipe -> {
                    viewModel.onAddButtonClicked()
                    true
                }
                R.id.search_recipe -> {
                    binding.searchBar.visibility = View.VISIBLE
                    setUpSearchView()
                    true
                }
                R.id.filter_recipe -> {
                    viewModel.onFilterButtonClicked()
                    true
                }
                else -> false
            }
        }

    }.root

}
