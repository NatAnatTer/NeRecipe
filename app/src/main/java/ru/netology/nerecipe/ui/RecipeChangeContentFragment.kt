package ru.netology.nerecipe.ui

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
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

                                val editedStep = viewModel.currentStep.value
                                addNumberOfStep.requestFocus()
                                addNumberOfStep.setText(
                                    editedStep?.numberOfStep?.toString() ?: ""
                                )
                                addStepDescription.setText(
                                    editedStep?.contentOfStep ?: ""
                                )
                                addStepUrl.setText(editedStep?.imageUrl ?: "")

                                saveStepButton.setOnClickListener {
                                    onSaveStepClicked(
                                        binding,
                                        editedStep,
                                        currentRecipe
                                    )
                                }
                            }
                        }
                    }
                }
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
        if(!validateSaveStep(binding))
            return
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
        if (editedStep != null) {

            if (viewModel.currentSteps.value?.contains(editedStep) == true) {

                viewModel.currentSteps.value = viewModel.currentSteps.value!!.map {
                    when {
                        it.stepId == editedStep.stepId && it.numberOfStep == editedStep.numberOfStep && it.imageUrl == editedStep.imageUrl ->

                            it.copy(
                                contentOfStep = savedStep.contentOfStep,
                                numberOfStep = savedStep.numberOfStep,
                                imageUrl = savedStep.imageUrl
                            )
                        else -> it
                    }
                }
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
        viewModel.currentStep.value = null
    }

    private fun validateSaveStep(binding:RecipeChangeCreateFragmentBinding): Boolean {

        if(binding.recipeChangeContentFragmentInclude.addNumberOfStep.text.toString() == ""){
            binding.recipeChangeContentFragmentInclude.addNumberOfStep.error = "Не задан номер шага"
            return false
        }
        if (binding.recipeChangeContentFragmentInclude.addStepDescription.text.toString() == ""){
            binding.recipeChangeContentFragmentInclude.addStepDescription.error = "Не задано описание шага"
            return false
        }
        return true
    }


    private fun validateRecipe (binding:RecipeChangeCreateFragmentBinding): Boolean {
        if(viewModel.currentSteps.value.isNullOrEmpty()) {
            val toast =  Toast.makeText(
                viewModel.getApplication(),
                "Отсутвует описание процесса приготовления",
                10
            )
            toast.show()
            return false
        }
        if (binding.recipeChangeContentFragmentInclude.addStepDescription.text.toString() == ""){
            binding.recipeChangeContentFragmentInclude.addStepDescription.error = "Не задано название рецепта"
            return false
        }
        return true
    }

    private fun onSaveRecipeButtonClicked(
        binding: RecipeChangeCreateFragmentBinding,
        currentRecipe: Recipe?,
        currentSteps: List<Steps>?,
        selectedCategory: CategoryOfRecipe
    ) {
        if(!validateRecipe(binding))
            return

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


            findNavController().popBackStack()
            viewModel.currentStep.value = null
            viewModel.currentSteps.value = null

        } else return
    }
}