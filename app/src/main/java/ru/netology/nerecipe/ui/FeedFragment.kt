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
import ru.netology.nerecipe.recipeWievModel.RecipeViewModel
import ru.netology.nmedia.adapter.RecipeAdapter

class FeedFragment : Fragment() {

    private val viewModel by viewModels<RecipeViewModel>(ownerProducer = ::requireParentFragment)

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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