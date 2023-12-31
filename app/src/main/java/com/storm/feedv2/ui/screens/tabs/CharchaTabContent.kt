package com.storm.feedv2.ui.screens.tabs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.storm.feedv2.ui.components.PostCard
import com.storm.feedv2.ui.screens.home.HomeViewModel

@Composable
fun CharchaTabContent(
    viewModel: HomeViewModel,
    navigateToSelectedPost: (Int) -> Unit
) {

    val state = viewModel.state
    val posts = viewModel.getAllPosts.collectAsLazyPagingItems()

    LazyColumn {
        items(count = posts.itemCount) {index->
            val post = posts[index]
            if (post != null) {
                PostCard(post = post, viewModel = viewModel){
                    navigateToSelectedPost(post.postId.toInt())
                    viewModel.updatePost(post)
                }
            }
        }

        if (posts.loadState.append == LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    }

}
