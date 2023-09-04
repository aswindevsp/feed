package com.storm.feedpage.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.storm.feedpage.R
import com.storm.feedpage.model.Post

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageHolder(post: Post) {
    val images = post.postImages
    val pagerState = rememberPagerState(pageCount = { images.size })

    if (post.postImages.size == 1) {
        Image(
            painter = painterResource(id = R.drawable.image1),
            contentDescription = "Post's image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 20.dp, max = 500.dp)
                .padding(top = 8.dp)
        )
    } else {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                .clip(RoundedCornerShape(16.dp))
        ) { page ->
            // Display post images
            Image(
                painter = painterResource(id = R.drawable.image1),
                contentDescription = "Post's image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 20.dp, max = 500.dp)
                    .padding(start = 2.dp, end = 2.dp)
//                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}