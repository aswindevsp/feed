package com.storm.feedpage.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.storm.feedpage.ui.components.AppDivider
import com.storm.feedpage.ui.components.TabsContainer
import com.storm.feedpage.ui.screens.tabs.BazaarTabContent
import com.storm.feedpage.ui.screens.tabs.ProfileTabContent
import com.storm.feedpage.ui.screens.tabs.CharchaTabContent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenUI(
    navigateToSelectedPost: (Int) -> Unit,
    viewModel: HomeViewModel
) {
    val homeTabs = listOf("Charcha", "Bazaar", "Profile")
    val homePagerState = rememberPagerState(pageCount = { homeTabs.size })

    Column(
        Modifier.fillMaxSize()
    ) {
        TabsContainer(tabs = homeTabs, pagerState = homePagerState)
        AppDivider(thickness = 1.dp, verticalPadding = 0.dp)
//        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        HorizontalPager(
            state = homePagerState
        ) {page ->
            when(page) {
                0 -> CharchaTabContent(navigateToSelectedPost = navigateToSelectedPost, viewModel = viewModel)
                1 -> BazaarTabContent()
                2 -> ProfileTabContent()
            }
        }

    }

}

