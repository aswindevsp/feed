package com.storm.feedv2.ui.screens.home

import com.storm.feedv2.model.Post


data class ScreenState(
    val isLoading: Boolean = false,
    val post: Post? = null,
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0,
    val postId: Int = -1
)