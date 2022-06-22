package ru.netology.nerecipe.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface RecipeDao {


    @Query("SELECT * FROM recipe")
    fun getAll(): LiveData<List<RecipeWithInfoEntity>>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: RecipeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSteps(steps: List<StepsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStepsTry(steps: StepsEntity)


//    @Transaction
//    fun insertRecipeSteps(recipe: RecipeEntity, steps: List<StepsEntity>){
//        insert(recipe)
//        insertSteps(steps)
//    }

    @Query("UPDATE recipe SET recipe_name = :recipeName WHERE recipe_id = :id")
    fun updateContentById(id: Long, recipeName: String)



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategory(category: List<CategoryOfRecipeEntity>)

    @Query("SELECT * FROM category_of_recipe")
    fun getAllCategory(): List<CategoryOfRecipeEntity>



//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    fun insertUser(user: UserEntity)
//
//    @Query("SELECT * FROM user")
//    fun getAllUsers(): List<UserEntity>
//
//    @Query("SELECT * FROM user WHERE user_name = :userName")
//    fun getMeUsers(userName: String): UserEntity





    @Query("DELETE FROM recipe WHERE recipe_id = :id")
    fun removeRecipeById(id: Long)
    @Query("DELETE FROM steps WHERE recipe_id = :id")
    fun removeStepsById(id: Long)


    @Transaction
    fun removeRecipe(idRecipe: Long){
        removeStepsById(idRecipe)
        removeRecipeById(idRecipe)
    }

    @Query("""
            UPDATE recipe SET
            is_favorites =  CASE WHEN is_favorites THEN 0 ELSE 1 END
            WHERE recipe_id = :recipeId
        """)
    fun favorite(recipeId: Long)

    @Query("SELECT * FROM steps WHERE recipe_id = :id")
    fun getStepsByRecipeId(id: Long): List<StepsEntity>

    @Query("SELECT * FROM recipe WHERE recipe_id = :id")
    fun getRecipeById(id: Long): RecipeEntity

//    @Query("SELECT * FROM category_of_recipe")
//    fun getAllCategory(): List<CategoryOfRecipeEntity>
//


//
//    @Query("SELECT * FROM posts WHERE id = :id")
//    fun getById(id: Long): PostEntity
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(post: PostEntity)
//
//    @Query("UPDATE posts SET content = :content WHERE id = :id")
//    fun updateContentById(id: Long, content:String)
//
//    @Query("""
//            UPDATE posts SET
//            likes = likes + CASE WHEN liked_by_me THEN -1 ELSE 1 END,
//            liked_by_me = CASE WHEN liked_by_me THEN 0 ELSE 1 END
//            WHERE id = :id
//        """)
//    fun likeByMe(id: Long)
//
//    @Query("DELETE FROM posts WHERE id = :id")
//    fun removeById(id: Long)
//
//
//    @Query("""
//            UPDATE posts SET
//            reposts = reposts + 1
//            WHERE id = :id
//        """)
//    fun repost(id: Long)

}


