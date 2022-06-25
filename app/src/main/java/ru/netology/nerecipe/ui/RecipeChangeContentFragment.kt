package ru.netology.nerecipe.ui

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fasterxml.jackson.databind.ObjectMapper
import ru.netology.nerecipe.adapter.RecipeStepsAdapter
import ru.netology.nerecipe.databinding.RecipeChangeCreateFragmentBinding
import ru.netology.nerecipe.dto.CategoryOfRecipe
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.dto.RecipeWithInfo
import ru.netology.nerecipe.dto.Steps
import ru.netology.nerecipe.recipeWievModel.RecipeViewModel


class RecipeChangeContentFragment : Fragment() {

    private val args by navArgs<RecipeChangeContentFragmentArgs>()
    private val viewModel by viewModels<RecipeViewModel>(ownerProducer = ::requireParentFragment)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RecipeChangeCreateFragmentBinding.inflate(layoutInflater)
        .also { binding ->
            val currentRecipe = viewModel.getRecipeById(args.idRecipe)

            //   val currentSteps = viewModel.getStepsByRecipeId(args.idRecipe).toMutableList()
            //____________experiment---------
            var newSteps: List<Steps>? = null

            with(binding.recipeChangeContentFragmentInclude) {
                insertRecipeName.setText(currentRecipe?.recipeName ?: "")
                insertRecipeName.requestFocus()
                authorName.text = currentRecipe?.authorName ?: "Me"

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
                category.setSelection(1)  // выделяем элемент
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
                                selection.text = "Category not selected"
                            } else {
                                val categoryString =
                                    viewModel.getCategoryById(currentRecipe.categoryId).categoryName
                                selection.text = categoryString
                            }
                        }
                    }


//


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
                                    val savedStep = onSaveStepClicked(
                                        binding,
                                        currentStepChoose,
                                        currentRecipe
                                    )

                                    if (currentStepChoose != null) {

                                        if (viewModel.currentSteps.value?.contains(currentStepChoose) == true) {

                                            listOf(
                                                viewModel.currentSteps.value?.find {
                                                    it?.stepId == currentStepChoose.stepId
                                                }?.copy(
                                                    contentOfStep = savedStep.contentOfStep,
                                                    numberOfStep = savedStep.numberOfStep,
                                                    imageUrl = savedStep.imageUrl
                                                )
                                            ) + viewModel.currentSteps.value //TODO no rerender exists step
                                            // TODO При создании все ID=0L
                                            //currentStepChoose = null
                                        } else viewModel.currentSteps.value =
                                            viewModel.currentSteps.value?.plus(
                                                savedStep
                                            )

                                    } else {
                                        viewModel.currentSteps.value =
                                            viewModel.currentSteps.value?.plus(
                                                savedStep
                                            ) ?: listOf(
                                                savedStep
                                            )
                                    }
                                    //TODO clear field
                                }
                            }
                        }

                    }
//-----------variant----
//                val adapterSteps = RecipeStepsAdapter(viewModel)
//                binding.recipeChangeContentFragmentInclude.recipeListRecyclerView.adapter =
//                    adapterSteps
//
//                viewModel.data.observe(viewLifecycleOwner) {
//                        adapterSteps.submitList(currentSteps) // this
//
//                      //  viewModel.currentStep.observe(viewLifecycleOwner) {
//                            with(binding.recipeChangeContentFragmentInclude) {
//                                val currentStepChoose = currentSteps
//                                addNumberOfStep.setText(
//                                    currentStepChoose?.numberOfStep?.toString() ?: ""
//                                )
//                                addStepDescription.setText(
//                                    currentStepChoose?.contentOfStep ?: ""
//                                )
//                                addStepUrl.setText(currentStepChoose?.imageUrl ?: "")
//                                addNumberOfStep.requestFocus()
//
//                                saveStepButton.setOnClickListener {
//                                    val savedStep = onSaveStepClicked(
//                                        binding,
//                                        currentStepChoose,
//                                        currentRecipe
//                                    )
//
//                                    if (currentStepChoose != null) {
//
//                                        if (viewModel.currentSteps.value?.contains(currentStepChoose) == true) {
//
//                                            listOf(viewModel.currentSteps.value?.find {
//                                                it?.stepId == currentStepChoose.stepId
//                                            }?.copy(
//                                                contentOfStep = savedStep.contentOfStep,
//                                                numberOfStep = savedStep.numberOfStep,
//                                                imageUrl = savedStep.imageUrl
//                                            ))+ viewModel.currentSteps.value //TODO no rerender exists step
//                                            // TODO При создании все ID=0L
//                                            //currentStepChoose = null
//                                        } else viewModel.currentSteps.value =
//                                            viewModel.currentSteps.value?.plus(
//                                                savedStep
//                                            )
//
//                                    } else {
//                                        viewModel.currentSteps.value =
//                                            viewModel.currentSteps.value?.plus(
//                                                savedStep
//                                            ) ?: listOf(
//                                                savedStep
//                                            )
//                                    }
//                                    //TODO clear field
//                                }
//                            }
                    //   }

                    //--------variant--

                }
                binding.recipeChangeContentFragmentInclude.saveRecipe.setOnClickListener {
                    onSaveRecipeButtonClicked(
                        binding,
                        currentRecipe,
                        viewModel.currentSteps.value,
                        categoryList
                    ) //TODO
                    //TODO on save step get method above save recipe
                }
            }


            //TODO clear  current steps when return or save recipe !!!!!!


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


    private fun onSaveStepClicked(
        binding: RecipeChangeCreateFragmentBinding,
        editedStep: Steps?,
        currentRecipe: Recipe?
    ): Steps {
        val newNumberStep =
            binding.recipeChangeContentFragmentInclude.addNumberOfStep.text.toString()
                .toInt()
        val newContentOfStep =
            binding.recipeChangeContentFragmentInclude.addStepDescription.text.toString()
        val newImageURL =
            if (binding.recipeChangeContentFragmentInclude.addStepUrl.text.toString() == "") null else binding.recipeChangeContentFragmentInclude.addStepUrl.text.toString()
        return Steps(
            stepId = editedStep?.stepId ?: 0L,
            numberOfStep = newNumberStep,
            contentOfStep = newContentOfStep,
            recipeId = currentRecipe?.recipeId ?: 0L,
            imageUrl = newImageURL
        )
    }

    private fun onSaveRecipeButtonClicked(
        binding: RecipeChangeCreateFragmentBinding,
        currentRecipe: Recipe?,
        currentSteps: List<Steps>?,
        categoryList: List<CategoryOfRecipe>
    ) {
        val newRecipeDescription =
            binding.recipeChangeContentFragmentInclude.insertRecipeName.text.toString()
        val newRecipeCategory = categoryList.find { it.categoryName == "Паназиатская" }
//            categoryList.find {
//            binding.recipeChangeContentFragmentInclude.category.adapter.toString() == it.categoryName
//        }

        var recipe: Recipe? = null
        if (currentRecipe == null) {
            if (newRecipeCategory != null) {
                recipe = Recipe(
                    recipeId = 0L,
                    recipeName = newRecipeDescription,
                    categoryId = newRecipeCategory.categoryId,
                    authorName = "Me",
                    isFavorites = false
                )
            }
        } else {
            if (newRecipeCategory != null) {
                recipe = currentRecipe.copy(
                    recipeName = newRecipeDescription,
                    categoryId = newRecipeCategory.categoryId,
                )
            }

        }
        val resultRecipe: RecipeWithInfo
        if (recipe != null && newRecipeCategory != null && currentSteps != null && !currentSteps.isNullOrEmpty()) {
            resultRecipe =
                RecipeWithInfo(recipe, newRecipeCategory, currentSteps)//recipe = recipe, category = newRecipeCategory, currentSteps)
            val resultBundle = Bundle(2)
            val contentNew = ObjectMapper().writeValueAsString(resultRecipe)//TODO
            resultBundle.putString(RESULT_KEY, contentNew)
            setFragmentResult(REQUEST_KEY, resultBundle)


            findNavController().popBackStack()

        } else return

    }

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