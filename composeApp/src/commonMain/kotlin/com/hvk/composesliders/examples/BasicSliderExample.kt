package com.hvk.composesliders.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicSliderExample() {
    var isEnabled by remember { mutableStateOf(true) }
    val sliderState = remember {
        SliderState(
            value = 50f,
            valueRange = 0f..100f,
            steps = 19 // Number of discrete steps between valueRange.start and valueRange.end
        )
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Volume: ${sliderState.value.roundToInt()}% (Range: 0-100)")
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Switch(
                checked = isEnabled,
                onCheckedChange = { isEnabled = it },
                Modifier.padding(8.dp)
            )

            Slider(
                state = sliderState,
                colors = SliderDefaults.colors(
                    thumbColor = Color.Red.copy(alpha = sliderState.value),
                    activeTrackColor = Color.Red.copy(alpha = .5f),
                    inactiveTrackColor = Color.Gray.copy(alpha = 0.3f),
                    activeTickColor = Color.Blue,
                    inactiveTickColor = Color.Gray
                ),
                enabled = isEnabled,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
@Preview
private fun Preview() {
    Surface {
        BasicSliderExample()
    }
}