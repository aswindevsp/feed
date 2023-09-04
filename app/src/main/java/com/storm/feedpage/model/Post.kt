package com.storm.feedpage.model

import android.graphics.drawable.Drawable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.storm.feedpage.util.Constants.POSTS_TABLE
import java.util.*

@Entity(tableName = POSTS_TABLE)
data class Post(
    @PrimaryKey val postId: Int,
    val postString: String,
    val postImages: List<Int>,
    @Embedded
    val author: Author,
    val postTime: Long,
    val postType: PostType,
    val likeCount: Int,
    val comments: List<Comment>
)

enum class PostType {
    QUESTION,
    MARKETING
}