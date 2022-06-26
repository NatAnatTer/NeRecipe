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


//<com.google.android.material.bottomnavigation.BottomNavigationView
//android:id="@+id/bottom_navigation_detail_recipe"
//style="@style/Widget.MaterialComponents.BottomNavigationView.Colored"
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:gravity="bottom"
//app:layout_constraintBottom_toBottomOf="parent"
//app:menu="@menu/bottom_navigation_menu_detail_recipe"
//tools:ignore="MissingConstraints" />


//
//<ScrollView
//android:layout_width="match_parent"
//android:layout_height="match_parent"
//app:layout_constraintBottom_toTopOf="@id/bottom_navigation"
//app:layout_constraintTop_toBottomOf="@id/barrier"
//tools:layout_editor_absoluteX="-186dp"
//tools:layout_editor_absoluteY="-203dp"
//tools:ignore="NotSibling">
//
//<include
//android:id="@+id/recipe_steps_list"
//layout="@layout/recipe_steps"
//android:layout_width="match_parent"
//android:layout_height="0dp"
//app:layout_constraintBottom_toTopOf="@id/bottom_navigation"
//app:layout_constraintTop_toBottomOf="@id/barrier" />
//</ScrollView>

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
//<androidx.appcompat.widget.AppCompatTextView
//android:id="@+id/recipeName"
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:layout_marginStart="16dp"
//android:layout_marginEnd="8dp"
//android:ellipsize="end"
//android:singleLine="true"
//android:textSize="24sp"
//android:textStyle="bold"
//app:layout_constraintBottom_toTopOf="@id/category"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toTopOf="parent"
//tools:ignore="MissingConstraints"
//tools:layout_editor_absoluteX="16dp"
//tools:text="Салат греческий" />
//
//<androidx.appcompat.widget.AppCompatTextView
//android:id="@+id/category"
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:layout_marginStart="16dp"
//android:layout_marginEnd="8dp"
//android:ellipsize="end"
//android:singleLine="true"
//
//android:textColor="@color/teal_700"
//android:textSize="18sp"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toBottomOf="@id/recipeName"
//
//tools:ignore="MissingConstraints,SmallSp"
//tools:layout_editor_absoluteX="16dp"
//tools:text="Средиземноморская" />
//
//<androidx.appcompat.widget.AppCompatTextView
//android:id="@+id/author"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginStart="16dp"
//android:layout_marginEnd="8dp"
//android:ellipsize="end"
//android:singleLine="true"
//android:text="@string/author"
//android:textSize="14sp"
//app:layout_constraintEnd_toStartOf="@id/authorName"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toBottomOf="@id/category" />
//
//<androidx.appcompat.widget.AppCompatTextView
//android:id="@+id/authorName"
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:layout_marginStart="4dp"
//android:layout_marginEnd="8dp"
//android:ellipsize="end"
//android:singleLine="true"
//android:textSize="14sp"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toEndOf="@id/author"
//app:layout_constraintTop_toBottomOf="@id/category"
//tools:text="Student of netology" />