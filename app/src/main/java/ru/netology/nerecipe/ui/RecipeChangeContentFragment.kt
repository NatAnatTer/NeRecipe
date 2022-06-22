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
import ru.netology.nerecipe.databinding.RecipeChangeContentFragmentBinding
import ru.netology.nerecipe.dto.CategoryOfRecipe
import ru.netology.nerecipe.recipeWievModel.RecipeViewModel


class RecipeChangeContentFragment : Fragment() {

    private val args by navArgs<RecipeChangeContentFragmentArgs>()
    private val viewModel by viewModels<RecipeViewModel>(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ) = RecipeChangeContentFragmentBinding.inflate(layoutInflater).also { binding ->

        val currentRecipe = viewModel.getRecipeById(args.idRecipe)

        with(binding) {
            insertRecipeName.setText(currentRecipe.recipeName)
            insertRecipeName.requestFocus()
            // category.
            authorName.text = currentRecipe.authorName


            //-------ADAPTER__________


            //Инициализируем элемент Spinner:
            category

            val categoryList = viewModel.getAllCategory()

            //Настраиваем слушатель нажатий Spinner(Click Listener в смысле):
            category.setOnItemSelectedListener(this)

            val adapter: ArrayAdapter<String> =
                ArrayAdapter(this, R.layout.simple_spinner_item, categoryList)

            // адаптер
//            val adapter1: ArrayAdapter<String> =
//                ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryList)

            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

            // val spinner = findViewById(R.id.spinner) as Spinner
            category.adapter = adapter
            // заголовок
            // заголовок
            category.prompt = "Title"
            // выделяем элемент
            // выделяем элемент
            category.setSelection(2)
            // устанавливаем обработчик нажатия
            // устанавливаем обработчик нажатия
            category.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?,
                    position: Int, id: Long
                ) {

                }

                override fun onNothingSelected(arg0: AdapterView<*>?) {}
            })


//        binding.edit.requestFocus()
//        if (args.initialContent != null) {
//            binding.headerText.text = getString(R.string.headerTextEdit)
//        } else {
//            binding.headerText.text = getString(R.string.headerTextCreate)
//        }
//        binding.returnFromCreatePost.setOnClickListener {
//            findNavController().popBackStack()
//        }
//        binding.ok.setOnClickListener {
//            onOkButtonClicked(binding)
//        }
        }
    }.root

    private fun <T> ArrayAdapter(
        recipeChangeContentFragmentBinding: RecipeChangeContentFragmentBinding,
        simpleSpinnerItem: Int,
        categoryList: List<CategoryOfRecipe>
    ) {
    }


    companion object {
        const val RESULT_KEY = "recipeNewContent"
        const val REQUEST_KEY = "requestKey"

    }
}
//
//
//    private val args by navArgs<PostContentFragmentArgs>()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//
//    ) = PostContentFragmentBinding.inflate(layoutInflater).also { binding ->
//        binding.edit.setText(args.initialContent)
//        binding.edit.requestFocus()
//        if (args.initialContent != null) {
//            binding.headerText.text = getString(R.string.headerTextEdit)
//        } else {
//            binding.headerText.text = getString(R.string.headerTextCreate)
//        }
//        binding.returnFromCreatePost.setOnClickListener {
//            findNavController().popBackStack()
//        }
//        binding.ok.setOnClickListener {
//            onOkButtonClicked(binding)
//        }
//
//    }.root
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
