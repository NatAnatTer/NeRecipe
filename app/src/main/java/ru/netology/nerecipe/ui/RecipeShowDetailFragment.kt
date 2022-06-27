package ru.netology.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import ru.netology.nerecipe.databinding.RecipeShowDetailFragmentBinding
import ru.netology.nerecipe.recipeWievModel.RecipeViewModel
import ru.netology.nerecipe.adapter.RecipeAdapter
import ru.netology.nerecipe.adapter.RecipeStepsAdapter
import ru.netology.nerecipe.dto.RecipeWithInfo


class RecipeShowDetailFragment : Fragment() {
    private val args by navArgs<RecipeShowDetailFragmentArgs>()
    private val viewModel by viewModels<RecipeViewModel>(ownerProducer = ::requireParentFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel.navigateToRecipeChangeContentScreenEvent.observe(this) { recipeId ->
            val direction = recipeId?.let { RecipeShowDetailFragmentDirections.toChangeContentFragmet(it) }
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
                val newRecipeContent =
                    mapper.readValue(newRecipeContentString, RecipeWithInfo::class.java)

                viewModel.onSaveButtonClicked(newRecipeContent)
            }

        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ) = RecipeShowDetailFragmentBinding.inflate(inflater, container, false)
            .also { binding ->

                val viewHolder = RecipeAdapter.ViewHolder(binding.recipeContentDetail, viewModel)
                viewModel.data.observe(viewLifecycleOwner) { recipe ->
                    val recipeCurrent = recipe.find { it.recipe.recipeId == args.initialContent } ?: run {
                        findNavController().navigateUp() // the post was deleted, close the fragment
                        return@observe
                    }
                    viewHolder.bind(recipeCurrent)
                }

                val adapter = RecipeStepsAdapter(viewModel)
                binding.recipeStepsListRecyclerView.adapter = adapter
                viewModel.data.observe(viewLifecycleOwner) {
                    val stepsOfRecipe = viewModel.getStepsByRecipeId(args.initialContent)
                    adapter.submitList(stepsOfRecipe)
                }


            }.root
}
