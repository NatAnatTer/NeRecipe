package ru.netology.nerecipe.ui

import android.R
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import ru.netology.nerecipe.adapter.RecipeStepsAdapter
import ru.netology.nerecipe.databinding.RecipeChangeCreateFragmentBinding
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.dto.Steps
import ru.netology.nerecipe.recipeWievModel.RecipeViewModel


class RecipeChangeContentFragment : Fragment() {

    private val args by navArgs<RecipeChangeContentFragmentArgs>()
    private val viewModel by viewModels<RecipeViewModel>(ownerProducer = ::requireParentFragment)

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

                binding.recipeChangeContentFragmentInclude.saveRecipe.setOnClickListener {
                    //     onSaveRecipeButtonClicked(binding) TODO
                }
//--------Add Step -------

                val adapterSteps = RecipeStepsAdapter(viewModel)
                binding.recipeChangeContentFragmentInclude.recipeListRecyclerView.adapter =
                    adapterSteps
                viewModel.currentSteps.observe(viewLifecycleOwner) {
                viewModel.data.observe(viewLifecycleOwner) {
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
                                                    it.copy(
                                                        contentOfStep = onSaveStepClicked(
                                                            binding,
                                                            currentStepChoose,
                                                            currentRecipe
                                                        ).contentOfStep,
                                                        numberOfStep = onSaveStepClicked(
                                                            binding,
                                                            currentStepChoose,
                                                            currentRecipe
                                                        ).numberOfStep,
                                                        imageUrl = onSaveStepClicked(
                                                            binding,
                                                            currentStepChoose,
                                                            currentRecipe
                                                        ).imageUrl
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
                                }

                            }
                        }
                    }

                }
                //TODO clear  current steps when return or save recipe !!!!!!

//currentStepChoose = Steps(binding.recipeChangeContentFragmentInclude.addNumberOfStep.text., )


//            binding.returnFromCreatePost.setOnClickListener {
//                findNavController().popBackStack() TODO
//            }
            }
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
) =
    Steps(
        stepId = editedStep?.stepId ?: 0L,
        numberOfStep = binding.recipeChangeContentFragmentInclude.addNumberOfStep.text.toString()
            .toInt(),
        contentOfStep = binding.recipeChangeContentFragmentInclude.addStepDescription.text.toString(),
        recipeId = currentRecipe?.recipeId ?: 0L,
        imageUrl = binding.recipeChangeContentFragmentInclude.addStepUrl.text.toString()
    )

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
