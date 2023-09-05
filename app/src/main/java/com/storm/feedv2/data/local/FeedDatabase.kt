package com.storm.feedv2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.storm.feedv2.data.local.dao.PostDao
import com.storm.feedv2.data.local.dao.RemoteKeysDao
import com.storm.feedv2.model.Post
import com.storm.feedv2.model.RemoteKeys

@Database(entities = [Post::class, RemoteKeys::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FeedDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao
    abstract fun remoteKeyDao(): RemoteKeysDao
}

