package com.storm.feedpage.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.storm.feedpage.util.Constants.COMMENTS_TABLE
import java.util.*

@Entity(tableName = COMMENTS_TABLE)
data class Comment(
    @PrimaryKey val commentId: Int,
    val userId: Int,
    val text: String,
    val likes: Int,
    val commentTime: Long
)

