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

  //  override val dataSteps: LiveData<Steps> = dao.getStepsByRecipeId()

    override fun delete(recipeId: Long) = dao.removeRecipe(recipeId)


    override fun save(recipe: Recipe): Long {
        val idrec = dao.insert(recipe.toEntity())
        return idrec
    }

    override fun saveSteps(step: Steps) {
        //  val insertedStep = step.
        dao.insertStepsTry(step.toEntity())
    }


    override fun createCategory(category: List<CategoryOfRecipe>) {
        val categoryList = dao.getAllCategory().map { it.toModel() }

        if (categoryList.isEmpty()) dao.insertCategory(category.map { it.toEntity() })
    }

    override fun favoritesByMe(recipeId: Long) = dao.favorite(recipeId)


    override fun getStepsByRecipeId(recipeId: Long): List<Steps> = dao.getStepsByRecipeId(recipeId).map{it.toModel()}

    override fun getRecipeById(recipeId: Long): Recipe =dao.getRecipeById(recipeId).toModel()

    override fun getAllCategory(): List<CategoryOfRecipe> = dao.getAllCategory().map { it.toModel() }

    override fun getCategoryById(categoryId: Long): CategoryOfRecipe = dao.getCategoryById(categoryId).toModel()
}


//else dao.updateContentById(recipe.recipeId, recipe.recipeName)

//
//
//
//    override val data = dao.getAll().map { entities ->
//        entities.map {
//            it.toModel()
//        }
//    }
//
//    override fun like(postId: Long) = dao.likeByMe(postId)
//
//
//    override fun delete(postId: Long) {
//        dao.removeById(postId)
//    }
//
//    override fun save(recipe: Recipe, steps: Array<Steps>) {
//        TODO("Not yet implemented")
//    }
//
//    override fun save(post: Post) {
//        if (post.id == RecipeRepository.NEW_POST_ID) dao.insert(post.toEntity())
//        else dao.updateContentById(post.id, post.content)
//    }
//
//    override fun getPost(postId: Long) = dao.getById(postId).toModel()
//
//    override fun repost(postId: Long) =
//        dao.repost(postId)
//
//
//}