package com.storm.feedpage.ui.screens.home

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.storm.feedpage.data.paging.DefaultPaginator
import com.storm.feedpage.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {


    var state by mutableStateOf(ScreenState())

    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            repository.getItems(nextPage, 10)
        },
        getNextKey = {
            state.page + 1
        },
        onError = {
            state = state.copy(error = it?.localizedMessage)
        },
        onSuccess = { posts, newKey ->
            state = state.copy(
                posts = state.posts + posts,
                page = newKey,
                endReached = posts.isEmpty()
            )
        }
    )

    init{
        loadNextItems()
        Log.d(TAG, "testing viewmodel")
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItem()
        }
    }

    fun updatePostId(postId: Int) {
        state = state.copy(postId = postId)
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

