package com.hvk.composesliders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hvk.composesliders.examples.BasicSliderExample
import com.hvk.composesliders.examples.TemperatureSliderExample
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * The main Composable function for the application.
 *
 * This function sets up the overall layout and includes different slider examples.
 * It uses MaterialTheme for styling and a Column for vertical arrangement of its children.
 * The content is scrollable and has padding.
 */
@Composable
@Preview
fun App() {
    MaterialTheme {
        val columnModifier = Modifier
            .safeContentPadding()
            .padding(8.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())

        Column(
            modifier = columnModifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            BasicSliderExample()
            HorizontalDivider()
            TemperatureSliderExample()
            HorizontalDivider()
        }
    }
}


