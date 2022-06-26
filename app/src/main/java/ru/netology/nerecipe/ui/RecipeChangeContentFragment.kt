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
            val currentCategory = currentRecipe?.categoryId?.toInt()?.minus(1) ?: 0
            var selectedCategory: CategoryOfRecipe? = null

            //   val currentSteps = viewModel.getStepsByRecipeId(args.idRecipe).toMutableList()
            //____________experiment---------
            //     var newSteps: List<Steps>? = null

            with(binding.recipeChangeContentFragmentInclude) {
                insertRecipeName.setText(currentRecipe?.recipeName ?: "")
                insertRecipeName.requestFocus()
                authorName.text = currentRecipe?.authorName ?: "Me"


                //ADAPTER:
                val categoryList = viewModel.getAllCategory()
                val categoryListString: List<String> = categoryList.map { it.categoryName }

                val adapter: ArrayAdapter<String> =
                    ArrayAdapter(
                        viewModel.getApplication(),
                        R.layout.simple_spinner_dropdown_item,
                        categoryListString
                    ) //Create Adapter
                category.adapter = adapter
                category.setSelection(currentCategory)  // выделяем элемент


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

                            selectedCategory = categoryList.find { it.categoryName == item }
                        }

                        override fun onNothingSelected(arg0: AdapterView<*>?) {
                            if (currentRecipe == null) {
                                binding.recipeChangeContentFragmentInclude.selection.text =
                                    "Category not selected"
                            } else {
                                val categoryString =
                                    viewModel.getCategoryById(currentRecipe.categoryId).categoryName
                                binding.recipeChangeContentFragmentInclude.selection.text =
                                    categoryString
                            }
                        }
                    }

                // Steps
                val adapterSteps = RecipeStepsAdapter(viewModel)
                binding.recipeChangeContentFragmentInclude.recipeListRecyclerView.adapter =
                    adapterSteps

                viewModel.data.observe(viewLifecycleOwner) {
                    viewModel.currentSteps.observe(viewLifecycleOwner) {

                        adapterSteps.submitList(viewModel.currentSteps.value)

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


                                saveStepButton.setOnClickListener {
                                    onSaveStepClicked(
                                        binding,
                                        currentStepChoose,
                                        currentRecipe
                                    )




//                                    val savedStep = onSaveStepClicked(
//                                        binding,
//                                        currentStepChoose,
//                                        currentRecipe
//                                    )

//                                    if (currentStepChoose != null) {
//
//                                        if (viewModel.currentSteps.value?.contains(currentStepChoose) == true) {
//
//                                            listOf(
//                                                viewModel.currentSteps.value?.find {
//                                                    it?.stepId == currentStepChoose.stepId
//                                                }?.copy(
//                                                    contentOfStep = savedStep.contentOfStep,
//                                                    numberOfStep = savedStep.numberOfStep,
//                                                    imageUrl = savedStep.imageUrl
//                                                )
//                                            ) + viewModel.currentSteps.value //TODO no rerender exists step
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
//                                    //TODO clear field //TODO clear fields with choose step under save
                                }
                            }
                        }
                    }
                }
                    //TODO on save step get method above save recipe
            }




            binding.bottomNavigationSaveContent.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    ru.netology.nerecipe.R.id.back -> {
                        viewModel.currentStep.value = null
                        viewModel.currentSteps.value = null
                        findNavController().popBackStack()
                        true
                    }
                    ru.netology.nerecipe.R.id.save_recipe -> {
                        selectedCategory?.let {
                            onSaveRecipeButtonClicked(
                                binding,
                                currentRecipe,
                                viewModel.currentSteps.value,
                                it
                            )
                        }
                        true
                    }
                    else -> false
                }
            }
        }.root

    companion object {
        const val RESULT_KEY = "recipeNewContent"
        const val REQUEST_KEY = "requestKey"

    }


    private fun onSaveStepClicked(
        binding: RecipeChangeCreateFragmentBinding,
        editedStep: Steps?,
        currentRecipe: Recipe?
    ) {




        val newNumberStep =
            binding.recipeChangeContentFragmentInclude.addNumberOfStep.text.toString()
                .toInt()
        val newContentOfStep =
            binding.recipeChangeContentFragmentInclude.addStepDescription.text.toString()
        val newImageURL = binding.recipeChangeContentFragmentInclude.addStepUrl.text.toString()

        val savedStep = Steps(
            stepId = editedStep?.stepId ?: 0L,
            numberOfStep = newNumberStep,
            contentOfStep = newContentOfStep,
            recipeId = currentRecipe?.recipeId ?: 0L,
            imageUrl = newImageURL
        )


        //_______перенесли
        if (editedStep != null) {

            if (viewModel.currentSteps.value?.contains(editedStep) == true) {

                listOf(
                    viewModel.currentSteps.value?.find {
                        it?.stepId == editedStep.stepId
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
        //TODO clear field //TODO clear fields with choose step under save



        //----------перенесли


    }

    private fun onSaveRecipeButtonClicked(
        binding: RecipeChangeCreateFragmentBinding,
        currentRecipe: Recipe?,
        currentSteps: List<Steps>?,
        selectedCategory: CategoryOfRecipe
    ) {
        val newRecipeDescription =
            binding.recipeChangeContentFragmentInclude.insertRecipeName.text.toString()


        val recipe: Recipe?
        recipe = currentRecipe?.copy(
            recipeName = newRecipeDescription,
            categoryId = selectedCategory.categoryId,
        )
            ?: Recipe(
                recipeId = 0L,
                recipeName = newRecipeDescription,
                categoryId = selectedCategory.categoryId,
                authorName = "Me",
                isFavorites = false
            )
        val resultRecipe: RecipeWithInfo
        if (!currentSteps.isNullOrEmpty()) {
            resultRecipe =
                RecipeWithInfo(
                    recipe,
                    selectedCategory,
                    currentSteps
                )
            val resultBundle = Bundle(2)
            val contentNew = ObjectMapper().writeValueAsString(resultRecipe)
            resultBundle.putString(RESULT_KEY, contentNew)
            setFragmentResult(REQUEST_KEY, resultBundle)

            viewModel.currentStep.value = null
            viewModel.currentSteps.value = null
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


//<com.google.android.material.appbar.AppBarLayout
//android:id="@+id/topAppBar"
//android:layout_width="match_parent"
//android:layout_height="wrap_content">
//
//<com.google.android.material.appbar.MaterialToolbar
//android:id="@+id/topAppBarOptions"
//android:layout_width="match_parent"
//android:layout_height="?attr/actionBarSize"
//app:title="@string/title_add_edit_recipe"
//app:navigationIcon="@drawable/ic_baseline_arrow_back_24"
//app:menu="@menu/top_navigation_edit_recipe"
//style="@style/Widget.MaterialComponents.Toolbar.Primary"
///>
//
//
//</com.google.android.material.appbar.AppBarLayout>


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