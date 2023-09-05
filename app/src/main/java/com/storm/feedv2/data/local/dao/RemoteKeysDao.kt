package com.storm.feedv2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.storm.feedv2.model.Post
import com.storm.feedv2.model.RemoteKeys

@Dao
interface RemoteKeysDao {
    @Query("select * from remote_keys_table where id =:id")
    suspend fun getRemoteKeys(id: String): RemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<RemoteKeys>)

    @Query("delete from remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}
