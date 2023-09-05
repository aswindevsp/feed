package com.storm.feedv2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Comment(
    @PrimaryKey val commentId: Int,
    val userId: Int,
    val text: String,
    val likes: Int,
    val commentTime: Long
)

