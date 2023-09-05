package com.storm.feedv2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class Author(
    val userId: Int,
    val username: String,
    val profileImage: String,
    val lastLoggedInTime: Long
)