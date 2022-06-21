package ru.netology.nerecipe.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nerecipe.databinding.FeedFragmentBinding
import ru.netology.nerecipe.dto.CategoryOfRecipe
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.dto.Steps
import ru.netology.nerecipe.recipeWievModel.RecipeViewModel
import ru.netology.nmedia.adapter.RecipeAdapter
import ru.netology.nmedia.data.RecipeRepository

class FeedFragment : Fragment() {

    private val viewModel by viewModels<RecipeViewModel>(ownerProducer = ::requireParentFragment)

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        //_________Zaglushka
        val categoryList = listOf(
            CategoryOfRecipe(
                categoryId = RecipeRepository.NEW_RECIPE_ID,
                categoryName = "Европейская"
            ),
            CategoryOfRecipe(
                categoryId = RecipeRepository.NEW_RECIPE_ID,
                categoryName = "Азиатская"
            ),
            CategoryOfRecipe(
                categoryId = RecipeRepository.NEW_RECIPE_ID,
                categoryName = "Паназиатская"
            ),
            CategoryOfRecipe(
                categoryId = RecipeRepository.NEW_RECIPE_ID,
                categoryName = "Восточная"
            ),
            CategoryOfRecipe(
                categoryId = RecipeRepository.NEW_RECIPE_ID,
                categoryName = "Американская"
            ),
            CategoryOfRecipe(
                categoryId = RecipeRepository.NEW_RECIPE_ID,
                categoryName = "Русская"
            ),
            CategoryOfRecipe(
                categoryId = RecipeRepository.NEW_RECIPE_ID,
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
                imageUrl = null
            ),
            Steps(
                stepId = 0L,
                numberOfStep = 2,
                contentOfStep = "secondStep",
                recipeId = 0L,
                imageUrl = null
            )
        )
        viewModel.onSaveButtonClicked(recipe, stepsList)


        ///// Zaglushka


        //
        viewModel.navigateToRecipeChangeContentScreenEvent.observe(this) { recipeId ->
            val direction = recipeId?.let { FeedFragmentDirections.toRecipeShowDetailFragment(it) }
            if (direction != null) {
                findNavController().navigate(direction)
            }

        }
        viewModel.navigateToShowRecipe.observe(this) { idPost ->
            val direction = idPost?.let { FeedFragmentDirections.toRecipeShowDetailFragment(it) }
            if (direction != null) {
                findNavController().navigate(direction)
            }
        }

    }

    override fun onResume() {
        super.onResume()

        setFragmentResultListener(requestKey = RecipeChangeContentFragment.REQUEST_KEY) { requestKey, bundle ->
            if (requestKey != RecipeChangeContentFragment.REQUEST_KEY) return@setFragmentResultListener
            val newPostContent =
                bundle.getString(RecipeChangeContentFragment.RESULT_KEY)
                    ?: return@setFragmentResultListener
          //  viewModel.onSaveButtonClicked(newPostContent)
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

//        binding.fab.setOnClickListener {
//            viewModel.onAddButtonClicked()
//        }

    }.root
}
