package ru.netology.nerecipe.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ru.netology.nerecipe.dto.RecipeWithInfo


@Dao
interface RecipeDao {

//@Query("SELECT recipe.recipe_id, recipe.recipe_name, recipe.author_id, recipe.category_id, user.user_name, category_of_recipe.category_name" +
//        " FROM recipe, user, category_of_recipe" +
//    " where recipe.author_id = user.user_id AND recipe.category_id = category_of_recipe.category_id")
    @Query("SELECT * FROM recipe")
    fun getAll(): LiveData<List<RecipeWithInfo>>

    //    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(post: PostEntity)


// ____________________________---
//    @Dao
//    interface DepartmentDao {
//        // ...
//        @get:Query("SELECT id, name from department")
//        val departmentsWithEmployees: List<Any?>?
//    }
//    ________________________________
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