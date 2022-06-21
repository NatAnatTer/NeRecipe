package ru.netology.nerecipe.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.room.Query
import ru.netology.nerecipe.db.*
import ru.netology.nerecipe.dto.*
import ru.netology.nmedia.data.RecipeRepository


class RecipeRepositoryImpl(
    private val dao: RecipeDao,
  //  private val daoRecipeSteps: RecipeStepDao
) : RecipeRepository {
    override val data: LiveData<List<RecipeWithInfo>> = dao.getAll().map{it.map{it2 -> it2.toModel()}}


    override fun delete(recipeId: Long) = dao.removeRecipe(recipeId)


//    override fun saveRecipeSteps(recipe: Recipe, steps: List<Steps>) {
//        dao.insertRecipeSteps(recipe.toEntity(), steps.map{it.toEntity()})
//    }

    override fun save(recipe: Recipe): Long {
       val idrec =  dao.insert(recipe.toEntity())
        return idrec
    }
    //    if (recipe.recipeId == RecipeRepository.NEW_RECIPE_ID) dao.insert(recipe.toEntity()) else 0L


    override fun saveSteps(step: Steps){
      //  val insertedStep = step.
        dao.insertStepsTry(step.toEntity())
    }


    override fun createCategory(category: List<CategoryOfRecipe>){
        val categoryList = dao.getAllCategory().map{it.toModel()}

        if (categoryList.isEmpty()) dao.insertCategory(category.map{it.toEntity()})
    }

    override fun favoritesByMe(recipeId: Long) = dao.favorite(recipeId)

//    override fun createUser(user: User) = dao.insertUser(user.toEntity())
//
//    override fun getCurrentUser(userName: String): User = dao.getMeUsers(userName).toModel()

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