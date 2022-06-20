package ru.netology.nerecipe.ui

import androidx.fragment.app.*


class RecipeShowDetailFragment : Fragment() {}
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