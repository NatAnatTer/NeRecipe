package ru.netology.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nerecipe.databinding.RecipeShowDetailFragmentBinding
import ru.netology.nerecipe.recipeWievModel.RecipeViewModel
import ru.netology.nmedia.adapter.RecipeAdapter


class RecipeShowDetailFragment : Fragment() {
    private val args by navArgs<RecipeShowDetailFragmentArgs>()
    private val viewModel by viewModels<RecipeViewModel>(ownerProducer = ::requireParentFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel.navigateToRecipeChangeContentScreenEvent.observe(this) { recipeId ->
            val direction = recipeId?.let { FeedFragmentDirections.toRecipeShowDetailFragment(it) }
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
        ) = RecipeShowDetailFragmentBinding.inflate(inflater, container, false)
            .also { binding ->
                val viewHolder = RecipeAdapter.ViewHolder(binding.recipeListItem, viewModel)
                viewModel.data.observe(viewLifecycleOwner) { recipe ->
                    val recipe = recipe.find { it.recipe.recipeId == args.idRecipe } ?: run {
                        findNavController().navigateUp() // the post was deleted, close the fragment
                        return@observe
                    }
                    viewHolder.bind(recipe)
                }
            }.root

    }



//<androidx.recyclerview.widget.RecyclerView
//android:id="@+id/recipeListRecyclerView"
//android:layout_width="match_parent"
//android:layout_height="0dp"
//app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
//app:layout_constraintBottom_toTopOf="@id/bottom_navigation"
//app:layout_constraintTop_toBottomOf="@id/barrier"
//tools:ignore="NotSibling"
//tools:listitem="@layout/recipe_steps">
//
//</androidx.recyclerview.widget.RecyclerView>
//
//    private val args by navArgs<PostShowContentFragmentArgs>()
//    private val viewModel by viewModels<PostViewModel>(ownerProducer = ::requireParentFragment)
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        viewModel.sharePostContent.observe(this) { postContent ->
//            val intent = Intent().apply {
//                action = Intent.ACTION_SEND
//                putExtra(Intent.EXTRA_TEXT, postContent)
//                type = "text/plain"
//            }
//            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
//            startActivity(shareIntent)
//        }
//
//        viewModel.videoLinkPlay.observe(this) { videoLink ->
//            val intent = Intent(Intent.ACTION_VIEW).apply {
//                val uri = Uri.parse(videoLink)
//                data = uri
//            }
//            val openVideoIntent =
//                Intent.createChooser(intent, getString(R.string.chooser_play_video))
//            startActivity(openVideoIntent)
//        }
//
//
//        viewModel.navigateToPostContentScreenEvent.observe(this) { initialContent ->
//            val direction = PostShowContentFragmentDirections.toPostContentFragment(initialContent)
//            findNavController().navigate(direction)
//        }
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
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ) = PostShowContentDetailFragmentBinding.inflate(inflater, container, false).also { binding ->
//        val viewHolder = PostsAdapter.ViewHolder(binding.postContentDetail, viewModel)
//        viewModel.data.observe(viewLifecycleOwner) { posts ->
//            val post = posts.find { it.id == args.idPost } ?: run {
//                findNavController().navigateUp() // the post was deleted, close the fragment
//                return@observe
//            }
//            viewHolder.bind(post)
//        }
//    }.root
//}