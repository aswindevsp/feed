package com.storm.feedv2.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.storm.feedv2.model.Post

@Dao
interface PostDao {

    @Query("select * from posts_table")
    fun getAllPosts(): PagingSource<Int, Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPosts(posts: List<Post>)

    @Query("delete from posts_table")
    suspend fun deleteAllImages()
}