package com.storm.feedpage.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.storm.feedpage.util.Constants.USER_TABLE

@Entity(tableName = USER_TABLE)
data class Author(
    @PrimaryKey val userId: Int,
    val username: String,
    val profileImage: String,
    val lastLoggedInTime: Long
)