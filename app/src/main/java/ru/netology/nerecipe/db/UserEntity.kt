package ru.netology.nerecipe.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "author_id")
    val authorId: Long,
    @ColumnInfo(name = "user_name")
    val userName: String
)