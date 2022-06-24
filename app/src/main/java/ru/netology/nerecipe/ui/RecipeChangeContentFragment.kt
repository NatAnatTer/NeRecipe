package ru.netology.nerecipe.ui

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import ru.netology.nerecipe.adapter.RecipeStepsAdapter
import ru.netology.nerecipe.databinding.RecipeChangeContentFragmentBinding
import ru.netology.nerecipe.databinding.RecipeChangeCreateFragmentBinding
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.dto.Steps
import ru.netology.nerecipe.recipeWievModel.RecipeViewModel


class RecipeChangeContentFragment : Fragment() {

    private val args by navArgs<RecipeChangeContentFragmentArgs>()
    private val viewModel by viewModels<RecipeViewModel>(ownerProducer = ::requireParentFragment)

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//
//        binding.topAppBar.setOnClickListener {
////                    findNavController().popBackStack()
////                } //TODO not working
////
////                binding.topAppBar.setNavigationOnClickListener {
////                    // Handle navigation icon press
////                }
////
////                binding.topAppBarOptions.setOnMenuItemClickListener{menuItem ->
////                    when (menuItem.itemId) {
////                        R.id. -> {
////                      //      onSaveRecipeButtonClicked(binding)
////                            // Handle favorite icon press
////                            true
////                        }
////                        else -> false
////                    }
////                }
//        }
//    }


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        getSupport
//    }
//        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//            val inflater = menu
//            inflater.inflate(R.menu.menu_main, menu)
//            return super.onCreateOptionsMenu(menu)
//        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ) = RecipeChangeCreateFragmentBinding.inflate(layoutInflater)
        .also { binding ->   //RecipeChangeContentFragmentBinding

            val currentRecipe = viewModel.getRecipeById(args.idRecipe)

            //   val currentSteps = viewModel.getStepsByRecipeId(args.idRecipe).toMutableList()


            with(binding.recipeChangeContentFragmentInclude) {
                insertRecipeName.setText(currentRecipe.recipeName)
                insertRecipeName.requestFocus()
                authorName.text = currentRecipe.authorName

                val categoryList = viewModel.getAllCategory()
                val categoryNameString: List<String> = categoryList.map { it.categoryName }

                val adapter: ArrayAdapter<String> =
                    ArrayAdapter(
                        viewModel.getApplication(),
                        R.layout.simple_spinner_dropdown_item,
                        categoryNameString
                    ) //Create Adapter


                category.onItemSelectedListener
                adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                category.adapter = adapter
                category.setSelection(2)  // выделяем элемент
                category.onItemSelectedListener =
                    object :
                        AdapterView.OnItemSelectedListener {    // устанавливаем обработчик нажатия
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int, id: Long
                        ) {
                            val item = parent?.getItemAtPosition(
                                position
                            ) as String
                            selection.text = item
                        }

                        override fun onNothingSelected(arg0: AdapterView<*>?) {
                            if (currentRecipe == null) {
                                selection.text = "Categorynot selected"
                            } else {
                                val categoryString =
                                    viewModel.getCategoryById(currentRecipe.categoryId).categoryName
                                selection.text = categoryString
                            }
                        }
                    }

//                binding.recipeChangeContentFragmentInclude.saveRecipe.setOnClickListener {
//                    //     onSaveRecipeButtonClicked(binding) TODO
//                }
//
//                <com.google.android.material.floatingactionbutton.FloatingActionButton
//                android:id="@+id/save_recipe"
//                android:layout_width="wrap_content"
//                android:layout_height="wrap_content"
//                android:layout_margin="@dimen/fab_margin"
//                android:contentDescription="@string/add_step"
//                app:layout_constraintBottom_toBottomOf="parent"
//                app:layout_constraintEnd_toEndOf="parent"
//                app:layout_constraintStart_toStartOf="parent"
//                app:srcCompat="@drawable/ic_add_24dp" />

//--------Add Step -------

                val adapterSteps = RecipeStepsAdapter(viewModel)
                binding.recipeChangeContentFragmentInclude.recipeListRecyclerView.adapter =
                    adapterSteps

                    viewModel.data.observe(viewLifecycleOwner) {
                        viewModel.currentSteps.observe(viewLifecycleOwner) {
                        adapterSteps.submitList(viewModel.currentSteps.value) // this

                        viewModel.currentStep.observe(viewLifecycleOwner) {
                            with(binding.recipeChangeContentFragmentInclude) {
                                val currentStepChoose = viewModel.currentStep.value
                                addNumberOfStep.setText(
                                    currentStepChoose?.numberOfStep?.toString() ?: ""
                                )
                                addStepDescription.setText(
                                    currentStepChoose?.contentOfStep ?: ""
                                )
                                addStepUrl.setText(currentStepChoose?.imageUrl ?: "")
                                addNumberOfStep.requestFocus()

                                saveStepButton.setOnClickListener {
                                    if (currentStepChoose != null) {
                                        viewModel.currentSteps.value?.forEach {

                                            if (it != null) {
                                                if (it.stepId == currentStepChoose.stepId) {
                                                    val savedStep = onSaveStepClicked(
                                                        binding,
                                                        currentStepChoose,
                                                        currentRecipe
                                                    )
                                                    it.copy(
                                                        contentOfStep = savedStep.contentOfStep,
                                                        numberOfStep = savedStep.numberOfStep,
                                                        imageUrl = savedStep.imageUrl
                                                    )
                                                }
                                            }

                                        }
                                    } else {
                                        viewModel.currentSteps.value =
                                            viewModel.currentSteps.value?.plus(
                                                onSaveStepClicked(
                                                    binding,
                                                    currentStepChoose,
                                                    currentRecipe
                                                )
                                            )
                                    }
                                    //TODO clear field
                                }

                            }
                        }
                    }

                }
                //TODO clear  current steps when return or save recipe !!!!!!

//currentStepChoose = Steps(binding.recipeChangeContentFragmentInclude.addNumberOfStep.text., )

//

            }

            //----top appbar------
//            binding.topAppBar.setOnClickListener {
//                    findNavController().popBackStack()
//                } //TODO not working
//
//                binding.topAppBar.setNavigationOnClickListener {
//                    // Handle navigation icon press
//                }
//
//                binding.topAppBarOptions.setOnMenuItemClickListener {
//                    when (it.itemId) {
//                        R.id.saveRecipe -> {
//                            //      onSaveRecipeButtonClicked(binding)
//                            // Handle favorite icon press
//                            true
//                        }
//                        else -> false
//                    }
//
//                }
            //------Top Appbar-------


        }.root

    companion object {
        const val RESULT_KEY = "recipeNewContent"
        const val REQUEST_KEY = "requestKey"

    }
}

private fun onSaveStepClicked(
    binding: RecipeChangeCreateFragmentBinding,
    editedStep: Steps?,
    currentRecipe: Recipe?
): Steps{
    val newNumberStep = binding.recipeChangeContentFragmentInclude.addNumberOfStep.getText().toString()
        .toInt()
    val newContentOfStep = binding.recipeChangeContentFragmentInclude.addStepDescription.getText().toString()
    val newImageURL = if (binding.recipeChangeContentFragmentInclude.addStepUrl.text.toString() == "") null else binding.recipeChangeContentFragmentInclude.addStepUrl.text.toString()
  return  Steps(
        stepId = editedStep?.stepId ?: 0L,
        numberOfStep = newNumberStep,
        contentOfStep = newContentOfStep,
        recipeId = currentRecipe?.recipeId ?: 0L,
        imageUrl = newImageURL
    )
}


//
//    private fun onOkButtonClicked(binding: PostContentFragmentBinding) {
//        val text = binding.edit.text
//        if (!text.isNullOrBlank()) {
//            val resultBundle = Bundle(1)
//            val content = text.toString()
//            resultBundle.putString(RESULT_KEY, content)
//            setFragmentResult(REQUEST_KEY, resultBundle)
//        }
//        findNavController().popBackStack()
//    }
//
//    companion object {
//        const val RESULT_KEY = "postNewContent"
//        const val REQUEST_KEY = "requestKey"
//
//    }
//}
//


//app:menu="@menu/top_navigation_edit_recipe"
