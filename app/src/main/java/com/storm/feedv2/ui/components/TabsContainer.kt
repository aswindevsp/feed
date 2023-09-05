package com.storm.feedv2.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabsContainer(
    modifier: Modifier = Modifier, tabs: List<String>, pagerState: PagerState
) {
    val coroutineScope = rememberCoroutineScope()

    TabRow(modifier = modifier.fillMaxWidth(),
        selectedTabIndex = pagerState.currentPage,
        divider = { },
        indicator = { tabPositions ->
            RoundedTabIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
            )
        }) {
        tabs.forEachIndexed { tabIndex, currentTab ->
            Tab(selected = pagerState.currentPage == tabIndex,
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.primary.copy(0.50f),
                onClick = { coroutineScope.launch { pagerState.animateScrollToPage(tabIndex) } },
                text = {
                    Text(
                        text = currentTab, style = MaterialTheme.typography.titleSmall
                    )
                })
        }
    }
}


@Composable
private fun RoundedTabIndicator(
    modifier: Modifier
) {
    Spacer(
        modifier
            .padding(horizontal = 40.dp)
            .height(2.dp)
            .background(
                MaterialTheme.colorScheme.primary, RoundedCornerShape(percent = 100)
            )
    )
}
