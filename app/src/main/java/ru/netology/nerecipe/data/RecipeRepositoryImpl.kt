package ru.netology.nerecipe.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.nerecipe.db.RecipeDao
import ru.netology.nerecipe.db.toEntity
import ru.netology.nerecipe.db.toModel
import ru.netology.nerecipe.dto.CategoryOfRecipe
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.dto.RecipeWithInfo
import ru.netology.nerecipe.dto.Steps


class RecipeRepositoryImpl(
    private val dao: RecipeDao,
) : RecipeRepository {

    override val data: LiveData<List<RecipeWithInfo>> =
        dao.getAll().map { it.map { it2 -> it2.toModel() } }

    override fun delete(recipeId: Long) = dao.removeRecipe(recipeId)

    override fun save(recipe: Recipe): Long = dao.insert(recipe.toEntity())

    override fun saveSteps(step: Steps) {
        dao.insertStepsTry(step.toEntity())
    }

    override fun createCategory(category: List<CategoryOfRecipe>) {
        val categoryList = dao.getAllCategory().map { it.toModel() }
        if (categoryList.isEmpty()) dao.insertCategory(category.map { it.toEntity() })
    }

    override fun favoritesByMe(recipeId: Long) = dao.favorite(recipeId)

    override fun getStepsByRecipeId(recipeId: Long): List<Steps> = dao.getStepsByRecipeId(recipeId).map{it.toModel()}

    override fun getRecipeById(recipeI: Long): Recipe = dao.getRecipeById(recipeI).toModel()

    override fun getAllCategory(): List<CategoryOfRecipe> = dao.getAllCategory().map { it.toModel() }

    override fun getCategoryById(categoryId: Long): CategoryOfRecipe = dao.getCategoryById(categoryId).toModel()
}
