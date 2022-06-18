package ru.netology.nerecipe.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.netology.nerecipe.dto.Steps


@Dao
interface RecipeDao {


    @Query("SELECT * FROM recipe ORDER BY recipe_id DESC")
    fun getAll(): LiveData<Map<List<RecipeEntity>, List<StepsEntity>, List<UserEntity>, List<CategoryOfRecipeEntity>, List<FavoritesEntity>>>

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