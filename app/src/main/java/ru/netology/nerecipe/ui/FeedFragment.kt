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
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import ru.netology.nerecipe.R
import ru.netology.nerecipe.databinding.FeedFragmentBinding
import ru.netology.nerecipe.dto.CategoryOfRecipe
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.dto.Steps
import ru.netology.nerecipe.recipeWievModel.RecipeViewModel
import ru.netology.nerecipe.adapter.RecipeAdapter
import ru.netology.nerecipe.data.RecipeRepository
import ru.netology.nerecipe.dto.RecipeWithInfo

class FeedFragment : Fragment() {

    private val viewModel by viewModels<RecipeViewModel>(ownerProducer = ::requireParentFragment)

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        //_________Zaglushka
        val categoryList = listOf(
            CategoryOfRecipe(
                categoryId = 1L,
                categoryName = "Европейская"
            ),
            CategoryOfRecipe(
                categoryId = 2L,
                categoryName = "Азиатская"
            ),
            CategoryOfRecipe(
                categoryId = 3L,
                categoryName = "Паназиатская"
            ),
            CategoryOfRecipe(
                categoryId = 4L,
                categoryName = "Восточная"
            ),
            CategoryOfRecipe(
                categoryId = 5L,
                categoryName = "Американская"
            ),
            CategoryOfRecipe(
                categoryId = 6L,
                categoryName = "Русская"
            ),
            CategoryOfRecipe(
                categoryId = 7L,
                categoryName = "Средиземноморская"
            )
        )
        viewModel.createCategory(categoryList)


        ////// Zaglushka

        val recipe = Recipe(
            recipeId = RecipeRepository.NEW_RECIPE_ID,
            recipeName = "First recipe",
            categoryId = 1L,
            authorName = "Me",
            isFavorites = false

        )
        val stepsList = listOf(
            Steps(
                stepId = 0L,
                numberOfStep = 1,
                contentOfStep = "firstStep",
                recipeId = 0L,
                imageUrl = "https://coderlessons.com/wp-content/uploads/2019/07/sample_image-5.jpg"
            ),
            Steps(
                stepId = 0L,
                numberOfStep = 2,
                contentOfStep = "secondStep",
                recipeId = 0L,
                imageUrl = null //"https://img1.freepng.ru/20171220/ide/donut-png-5a3ac1b8c33b25.9691611515138001207997.jpg"
            )
        )



        ///// Zaglushka


        viewModel.navigateToRecipeChangeContentScreenEvent.observe(this) {idRecipe ->
            val direction = idRecipe?.let { FeedFragmentDirections.toChangeContentFragment(it) }?: FeedFragmentDirections.toChangeContentFragment(0L)
            if (direction != null) {
                findNavController().navigate(direction)
            }

        }
        viewModel.navigateToShowRecipe.observe(this) { recipeId ->
            val direction = recipeId?.let { FeedFragmentDirections.toRecipeShowDetailFragment(it) }
            if (direction != null) {
                findNavController().navigate(direction)
            }
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
            val newRecipeContent = mapper.readValue(newRecipeContentString, RecipeWithInfo::class.java)

            viewModel.onSaveButtonClicked(newRecipeContent)
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
            adapter.submitList(recipe)
        }
binding.searchBar.visibility = View.GONE



        //-------search
        fun setUpSearchView() {
            binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    adapter.getFilter().filter(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.getFilter().filter(newText);
                    return true
                }

            })
        }

//---------search
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.recipe_list -> {
                    // Respond to navigation item 1 click
                    true
                }
                R.id.favorites_list -> {
                    // Respond to navigation item 2 click
                    true
                }
                R.id.add_recipe -> {
                    viewModel.onAddButtonClicked()
                    true
                }
                R.id.search_recipe -> {
                    binding.searchBar.visibility = View.VISIBLE
                    true
                }
                R.id.filter_recipe -> {
                    // Respond to navigation item 2 click
                    true
                }
                else -> false
            }
        }

//        binding.fab.setOnClickListener {
//            viewModel.onAddButtonClicked()
//        }

    }.root
}
