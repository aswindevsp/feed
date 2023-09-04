package com.storm.feedpage.ui.screens.home

import com.storm.feedpage.model.Post

data class ScreenState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0,
    val postId: Int = -1
)