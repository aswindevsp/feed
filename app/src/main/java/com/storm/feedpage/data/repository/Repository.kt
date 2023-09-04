package com.storm.feedpage.data.repository

import com.storm.feedpage.model.Author
import com.storm.feedpage.model.Comment
import com.storm.feedpage.model.Post
import com.storm.feedpage.model.PostType
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

import kotlin.random.Random

@Singleton

class Repository @Inject constructor() {

    private val remoteDataSource = (1..100).map { postId ->
        val postString = generateRandomPostString()
        val selectedImages = generateRandomImages()
        val author = Author(
            userId = Random.nextInt(1, 1000),
            username = "User $postId",
            profileImage = "ProfileImage $postId",
            lastLoggedInTime =  System.currentTimeMillis() - Random.nextLong(0, 7 * 24 * 60 * 60 * 1000)
        )
        val postTime = System.currentTimeMillis() - Random.nextLong(0, 7 * 24 * 60 * 60 * 1000)
        val postType = if (Random.nextBoolean()) PostType.QUESTION else PostType.MARKETING
        val likeCount = Random.nextInt(0, 100)
        val comments = generateRandomComments()

        Post(
            postId = postId,
            postString = postString,
            postImages = selectedImages,
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
        val numComments = Random.nextInt(1, 5)
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

    suspend fun getItems(page: Int, pageSize: Int): Result<List<Post>> {
        delay(1000)
        val startingIndex = page * pageSize
        return if (startingIndex < remoteDataSource.size) {
            Result.success(
                remoteDataSource.slice(startingIndex until minOf(startingIndex + pageSize, remoteDataSource.size))
            )
        } else Result.success(emptyList())
    }
}

