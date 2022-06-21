package ru.netology.nerecipe.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import ru.netology.nerecipe.db.RecipeDao
import ru.netology.nerecipe.db.RecipeWithInfoEntity
import ru.netology.nerecipe.db.toEntity
import ru.netology.nerecipe.db.toModel
import ru.netology.nerecipe.dto.*
import ru.netology.nmedia.data.RecipeRepository


class RecipeRepositoryImpl(
    private val dao: RecipeDao
) : RecipeRepository {
    override val data: LiveData<List<RecipeWithInfo>> = dao.getAll().map{it.map{it2 -> it2.toModel()}}




    override fun delete(recipeId: Long) {
        TODO("Not yet implemented")
    }


    override fun save(recipe: Recipe) {
        if (recipe.recipeId == RecipeRepository.NEW_RECIPE_ID) dao.insert(recipe.toEntity())
       // steps.forEach { dao.insertSteps(it.toEntity()) }
    }
    override fun saveSteps(step: Steps){
        dao.insertSteps(step.toEntity())
    }


    override fun createCategory(category: List<CategoryOfRecipe>){
        val categoryList = dao.getAllCategory().map{it.toModel()}

        if (categoryList.isEmpty()) dao.insertCategory(category.map{it.toEntity()})
    }

    override fun createUser(user: User){

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