package com.storm.feedv2.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.storm.feedv2.data.local.FeedDatabase
import com.storm.feedv2.model.Author
import com.storm.feedv2.model.Comment
import com.storm.feedv2.model.Post
import com.storm.feedv2.model.RemoteKeys
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

@OptIn(ExperimentalPagingApi::class)
class FeedRemoteMediator @Inject constructor(
    private val feedDatabase: FeedDatabase
): RemoteMediator<Int, Post>(){

    private val postDao = feedDatabase.postDao()
    private val remoteKeysDao = feedDatabase.remoteKeyDao()


    override suspend fun load(loadType: LoadType, state: PagingState<Int, Post>): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = getItems(currentPage, 10)
            val endOfPaginationReached = response.isEmpty()


            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            feedDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    postDao.deleteAllImages()
                    remoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.map { post ->
                    RemoteKeys(
                        id = post.postId,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                remoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                postDao.addPosts(posts = response)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        }catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }



    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Post>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.postId?.let { id ->
                remoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Post>
    ): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { unsplashImage ->
                remoteKeysDao.getRemoteKeys(id = unsplashImage.postId)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Post>
    ): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { post ->
                remoteKeysDao.getRemoteKeys(id = post.postId)
            }
    }

    private val remoteDataSource = (1..100).map { id ->
        val postString = generateRandomPostString()
        val postImages = generateRandomImages()
        val author = Author(
            userId = Random.nextInt(1, 1000),
            username = "John Snow",
            profileImage = "ProfileImage",
            lastLoggedInTime =  System.currentTimeMillis() - Random.nextLong(0, 7 * 24 * 60 * 60 * 1000)
        )
        val postTime = System.currentTimeMillis() - Random.nextLong(0, 7 * 24 * 60 * 60 * 1000)
        val postType = "Type #$id" // Replace this with your desired post type
        val likeCount = Random.nextInt(0, 100) // Generate a random number of likes
        val comments = generateRandomComments()

        Post(
            postId = id.toString(),
            postString = postString,
            postImages = postImages,
            author = author,
            postTime = postTime,
            postType = postType,
            likeCount = likeCount,
            comments = comments
        )
    }


    private fun generateRandomPostString(): String {
        val postOptions = listOf(
            "This is a post without an image.",
            "Just sharing my thoughts...",
            "No image today.",
            "Posting some text.",
            "Random text for the post."
        )
        return postOptions.random()
    }


    private fun generateRandomImages(): List<Int> {
        val selectedImages = mutableListOf<Int>()
        val numImages = Random.nextInt(0, 3)
        for (i in 1..numImages) {
            selectedImages.add(i)
        }
        return selectedImages
    }
    private fun generateRandomComments(): List<Comment> {
        val numComments = Random.nextInt(1, 10)
        val comments = mutableListOf<Comment>()
        for (i in 1..numComments) {
            val commentId = i // Use 'i' as a unique commentId
            val userId = Random.nextInt(1, 1000)
            val commentText = "This is comment $i"
            val likes = Random.nextInt(1, 100)
            val commentTime = System.currentTimeMillis() - Random.nextLong(0, 7 * 24 * 60 * 60 * 1000)
            comments.add(Comment(commentId, userId, commentText,likes, commentTime))
        }
        return comments
    }

    suspend fun getItems(page: Int, pageSize: Int): List<Post> {
        delay(1000)
        val startingIndex = page * pageSize
        return if (startingIndex < remoteDataSource.size) {
            remoteDataSource.slice(startingIndex until minOf(startingIndex + pageSize, remoteDataSource.size))
        } else emptyList()
    }


}