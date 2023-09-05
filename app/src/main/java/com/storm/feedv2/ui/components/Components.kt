package com.storm.feedv2.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AppDivider(
    verticalPadding: Dp,
    thickness: Dp = 1.dp
) {
    Divider(
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
        thickness = thickness,
        modifier = Modifier.padding(vertical = verticalPadding)
    )
}
