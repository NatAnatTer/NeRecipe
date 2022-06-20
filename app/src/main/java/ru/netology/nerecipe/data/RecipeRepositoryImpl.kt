package ru.netology.nerecipe.data

import androidx.lifecycle.LiveData
import ru.netology.nerecipe.db.RecipeDao
import ru.netology.nerecipe.db.toEntity
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.dto.RecipeWithInfo
import ru.netology.nerecipe.dto.Steps
import ru.netology.nmedia.data.RecipeRepository


class RecipeRepositoryImpl(
    private val dao: RecipeDao
) : RecipeRepository {
    override val data: LiveData<List<RecipeWithInfo>> = dao.getAll()
    override fun delete(recipeId: Long) {
        TODO("Not yet implemented")
    }


    override fun save(recipe: Recipe) {
        if (recipe.recipeId == RecipeRepository.NEW_RECIPE_ID) dao.insert(recipe.toEntity())
       // steps.forEach { dao.insertSteps(it.toEntity()) }
    }
    fun saveSteps(step: Steps){
        dao.insertSteps(step.toEntity())
    }
    //else dao.updateContentById(recipe.recipeId, recipe.recipeName)

}


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