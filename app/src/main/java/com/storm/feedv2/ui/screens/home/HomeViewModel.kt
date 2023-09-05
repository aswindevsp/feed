package com.storm.feedv2.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.filter
import com.storm.feedv2.data.repository.Repository
import com.storm.feedv2.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    var state by mutableStateOf(ScreenState())
    val getAllPosts = repository.getAllPosts()


    fun updatePostId(postId: Int) {
        state = state.copy(postId = postId)
    }

    fun updatePost(post: Post) {
        state = state.copy(post = post)
    }


    fun timeAgo(lastLoggedInTime: Long): String {
        val currentTimeMillis = System.currentTimeMillis()
        val timeDifferenceMillis = currentTimeMillis - lastLoggedInTime

         return when {
            timeDifferenceMillis < 60 * 1000 -> "Just now"
            timeDifferenceMillis < 60 * 60 * 1000 -> {
                val minutes = (timeDifferenceMillis / (60 * 1000)).toInt()
                "$minutes ${if (minutes == 1) "minute" else "minutes"} ago"
            }

            timeDifferenceMillis < 24 * 60 * 60 * 1000 -> {
                val hours = (timeDifferenceMillis / (60 * 60 * 1000)).toInt()
                "$hours ${if (hours == 1) "hour" else "hours"} ago"
            }

            else -> {
                val days = (timeDifferenceMillis / (24 * 60 * 60 * 1000)).toInt()
                "$days ${if (days == 1) "day" else "days"} ago"
            }
        }
    }
}

