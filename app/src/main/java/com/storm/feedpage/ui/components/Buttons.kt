package com.storm.feedpage.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ModeComment
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.storm.feedpage.model.Post

@Composable
fun LikeButton(isLiked: Boolean, likeCount: Int,  onClick: () -> Unit) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Row(
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Crossfade(targetState = isLiked, label = "") { liked ->
            if (liked) {
                Icon(
                    Icons.Rounded.Favorite,
                    contentDescription = "Unlike icon",
                    tint = MaterialTheme.colorScheme.primary
                )
            } else {
                Icon(
                    Icons.Rounded.FavoriteBorder,
                    contentDescription = "Like icon"
                )
            }
        }
        Text(
            text = "$likeCount likes",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


@Composable
fun CommentButton(
    navigateToSelectedPost: (Int) -> Unit,
    post: Post
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }
    Row(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { navigateToSelectedPost(post.postId)},
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        val commentCount = post.comments.size
        Icon(Icons.Outlined.ModeComment, contentDescription = "Comment icon")
        Text(
            text = "$commentCount Comments",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ShareButton() {
    Row(
        modifier = Modifier.clickable {  },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Outlined.Share, contentDescription = "Share")
        Text(
            text = "Share",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}