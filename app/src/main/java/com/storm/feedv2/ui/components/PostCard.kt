package com.storm.feedv2.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.storm.feedv2.R
import com.storm.feedv2.model.Post
import com.storm.feedv2.ui.screens.home.HomeViewModel


@Composable
fun PostCard(post: Post, viewModel: HomeViewModel, navigateToSelectedPost: (Int) -> Unit) {
    Column(
    ) {
        PostHeader(post = post, viewModel = viewModel)
        PostBody(post = post)
        PostFooter(post = post, navigateToSelectedPost = navigateToSelectedPost)
    }

    Divider(color = MaterialTheme.colorScheme.inverseOnSurface)
}


@Composable
fun PostHeader(post: Post, viewModel: HomeViewModel) {
    Box(modifier = Modifier.padding(12.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(42.dp),
                painter = painterResource(id = R.drawable.profile), contentDescription = "Profile"
            )

            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = "John Snow",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = viewModel.timeAgo(post.postTime),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            Box(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
            ) {
                // Display post type
//                Text(
//                    text = post.postType.name,
//                    modifier = Modifier.padding(4.dp),
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = MaterialTheme.colorScheme.primary
//                )
            }
        }

        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .rotate(90f)
                .padding(12.dp),
            imageVector = Icons.Default.MoreVert,
            contentDescription = null
        )
    }
}

@Composable
fun PostBody(post: Post) {
    Column {
        Text(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp),
            text = post.postString,
            style = MaterialTheme.typography.bodyLarge
        )
        if (post.postImages.isNotEmpty())
            ImageHolder(post = post)
    }
}



@Composable
fun PostFooter(post: Post, navigateToSelectedPost: (Int) -> Unit) {
    var isLiked by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LikeButton(
            isLiked = isLiked, likeCount = post.likeCount,
            onClick = {
                isLiked = !isLiked
            }
        )

        CommentButton(
            navigateToSelectedPost, post
        )

        ShareButton()
    }
}

