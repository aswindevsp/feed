package com.storm.feedv2.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.storm.feedv2.data.local.FeedDatabase
import com.storm.feedv2.data.paging.FeedRemoteMediator
import com.storm.feedv2.model.Post
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class Repository @Inject constructor(
    private val feedDatabase: FeedDatabase
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getAllPosts(): Flow<PagingData<Post>> {
        val pagingSourceFactory = { feedDatabase.postDao().getAllPosts() }
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = FeedRemoteMediator(
                feedDatabase = feedDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

}