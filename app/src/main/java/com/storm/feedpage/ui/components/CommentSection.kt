package com.storm.feedpage.ui.components

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.storm.feedpage.R
import com.storm.feedpage.model.Comment
import com.storm.feedpage.model.Post
import com.storm.feedpage.ui.components.LikeButton
import com.storm.feedpage.ui.screens.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentSection(
    viewModel: HomeViewModel,
    postId: Int,
    navController: NavController
) {

    val state = viewModel.state
    val post = state.posts.find { it.postId == postId }!!


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Comments", style = MaterialTheme.typography.titleSmall) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go back")
                    }
                }
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn() {
                item {
                    UpperCommentBody(post)
                }
                items(post.comments.size) {
                    CommentCard(post.comments[it])
                    Divider(
                        thickness = 1.dp
                    )
                }
            }


        }
    }
}


@Composable
fun UpperCommentBody(post: Post) {
    PostHeader(post = post)
    PostBody(post = post)
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(12.dp),
            text = "${post.comments.size} Comments",
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium
        )

        Row(
            Modifier
                .padding(12.dp)
                .align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowRight, contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )


            Text(
                text = "Recent",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@Composable
fun CommentCard(
    comment: Comment
) {
    val isLiked = remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.padding(12.dp)){
        CommentCardHeader(comment = comment)
        Text(
            text = comment.text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.
                padding(top = 2.dp, bottom = 8.dp)
        )
        LikeButton(
            isLiked = isLiked.value, likeCount = comment.likes
        ) {
            isLiked.value = !isLiked.value
        }
    }
}

@Composable
fun CommentCardHeader(comment: Comment) {
    Box(modifier = Modifier) {
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
                    text = "Public",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }

        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(12.dp),
            imageVector = Icons.Default.MoreVert,
            contentDescription = null
        )
    }
}
