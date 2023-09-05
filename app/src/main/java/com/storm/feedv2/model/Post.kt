package com.storm.feedv2.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "posts_table")
data class Post(
    @PrimaryKey (autoGenerate = false)
    val postId: String,
    val postString: String,
    val postImages: List<Int>,
    @Embedded
    val author: Author,
    val postTime: Long,
    val postType: String,
    val likeCount: Int,
    val comments: List<Comment>,
)