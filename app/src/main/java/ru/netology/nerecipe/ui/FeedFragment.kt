package ru.netology.nerecipe.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
            recipeName = "Firs",
            categoryId = 0L,
            authorId = 0L

        )

        fun onSaveButtonClicked(
            recipeName: String,
            authorId: Long,
            categoryId: Long,
            steps: Array<Steps>
        ) {
            if (steps.isEmpty()) return

            val newRecipe = currentRecipe.value?.copy(
                recipeName = recipeName,
                categoryId = categoryId,

                ) ?: Recipe(
                recipeId = RecipeRepository.NEW_RECIPE_ID,
                recipeName = recipeName,
                categoryId = categoryId,
                authorId = authorId
            )
            steps.forEach {
                Steps(
                    stepId = 0L,
                    numberOfStep = it.numberOfStep,
                    contentOfStep = it.contentOfStep,
                    recipeId = it.recipeId,
                    imageUrl = it.imageUrl
                )
                repository.saveSteps(it)
            }
            // repository.saveSteps(steps)
            repository.save(newRecipe)
            currentRecipe.value = null
        }
        ///// Zaglushka

    }


    //
//        viewModel.navigateToRecipeContentScreenEvent.observe(this) { recipeId ->
//            val direction = FeedFragmentDirections.toPostContentFragment(recipeId)
//            findNavController().navigate(direction)
//
//        }
//        viewModel.navigateToShowPost.observe(this) { idPost ->
//            val direction = idPost?.let { FeedFragmentDirections.toPostShowContentFragment(it) }
//            if (direction != null) {
//                findNavController().navigate(direction)
//            }
//        }
//
//    }
//
//    override fun onResume() {
//        super.onResume()
//
//        setFragmentResultListener(requestKey = RecipeChangeContentFragment.REQUEST_KEY) { requestKey, bundle ->
//            if (requestKey != RecipeChangeContentFragment.REQUEST_KEY) return@setFragmentResultListener
//            val newPostContent =
//                bundle.getString(RecipeChangeContentFragment.RESULT_KEY) ?: return@setFragmentResultListener
//            viewModel.onSaveButtonClicked(newPostContent)
//        }
//
//    }
//
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
//
//    companion object {
//        const val TAG = "FeedFragment"
//    }
//}