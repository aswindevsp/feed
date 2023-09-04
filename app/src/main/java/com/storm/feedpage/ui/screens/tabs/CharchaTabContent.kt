package com.storm.feedpage.ui.screens.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.storm.feedpage.ui.screens.home.HomeViewModel
import com.storm.feedpage.ui.components.PostCard

@Composable
fun CharchaTabContent(
    viewModel: HomeViewModel,
    navigateToSelectedPost: (Int) -> Unit
) {

    val state = viewModel.state
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.posts.size) { i ->
                val item = state.posts[i]
                if (i >= state.posts.size - 1 && !state.endReached && !state.isLoading) {
                    viewModel.loadNextItems()
                }
//                PostCard(post = item, navigateToSelectedPost)
                PostCard(post = item){
                    navigateToSelectedPost(item.postId)
                    viewModel.updatePostId(item.postId)
                }

            }
            item {
                if (state.isLoading) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }

}
