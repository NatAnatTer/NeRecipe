package ru.netology.nerecipe.ui

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.netology.nerecipe.dto.CategoryOfRecipe
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.dto.RecipeWithInfo
import ru.netology.nerecipe.dto.Steps

class RecipeChangeContentFragmentTest {

    @Test
    fun newRecipeTest() {
        val currentRecipe: Recipe? = null
        val newRecipeDescription = "1 Recipe"
        val selectedCategory = CategoryOfRecipe(
            categoryId = 1L,
            categoryName = "Европейская"
        )

        val recipe = Recipe(
            recipeId = 0L,
            recipeName = newRecipeDescription,
            categoryId = selectedCategory.categoryId,
            authorName = "Me",
            isFavorites = false
        )

        val result = RecipeChangeContentFragment().newRecipe(
            currentRecipe,
            newRecipeDescription,
            selectedCategory
        )

        assertEquals(recipe, result)
    }


    @Test
    fun newRecipeEdited_Test() {
        val currentRecipe = Recipe(
            recipeId = 1L,
            recipeName = "Recipe 1",
            categoryId = 3L,
            authorName = "Me",
            isFavorites = true
        )
        val newRecipeDescription = "1 Recipe"
        val selectedCategory = CategoryOfRecipe(
            categoryId = 1L,
            categoryName = "Европейская"
        )

        val recipe = Recipe(
            recipeId = currentRecipe.recipeId,
            recipeName = newRecipeDescription,
            categoryId = selectedCategory.categoryId,
            authorName = "Me",
            isFavorites = currentRecipe.isFavorites
        )

        val result = RecipeChangeContentFragment().newRecipe(
            currentRecipe,
            newRecipeDescription,
            selectedCategory
        )

        assertEquals(recipe, result)
    }

    private val mapper = ObjectMapper().registerKotlinModule()

    @Test
    fun putObjectIntoBundleTest() {

        val recipeWithInfo =
            RecipeWithInfo(
                recipe = Recipe(
                    recipeId = 0L,
                    recipeName = "1recipe",
                    categoryId = 4L,
                    authorName = "Me",
                    isFavorites = false
                ),
                category = CategoryOfRecipe(categoryId = 4L, categoryName = "Паназиатская"),
                steps = listOf(
                    Steps(
                        stepId = 0L,
                        numberOfStep = 1,
                        contentOfStep = "1 step",
                        recipeId = 0L,
                        imageUrl = ""
                    )
                )
            )
        val expectResult =
            "{\"recipe\":{\"recipeId\":0,\"recipeName\":\"1recipe\",\"categoryId\":4,\"authorName\":\"Me\",\"favorites\":false},\"category\":{\"categoryId\":4,\"categoryName\":\"Паназиатская\"},\"steps\":[{\"stepId\":0,\"numberOfStep\":1,\"contentOfStep\":\"1 step\",\"recipeId\":0,\"imageUrl\":\"\"}]}"
        assertEquals(expectResult, mapper.writeValueAsString(recipeWithInfo))
    }
}