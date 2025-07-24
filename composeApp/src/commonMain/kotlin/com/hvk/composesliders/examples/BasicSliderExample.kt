package com.hvk.composesliders.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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

@Composable
fun BasicSliderExample() {
    var volume by remember { mutableFloatStateOf(50f) }
    var isEnabled by remember { mutableStateOf(true) }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Volume: ${volume.roundToInt()}% (Range: 0-100)")
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
                value = volume,
                onValueChange = { volume = it },
                valueRange = 0f..100f,
                colors = SliderDefaults.colors(
                    thumbColor = Color.Red.copy(alpha = volume ),
                    activeTrackColor = Color.Red.copy(alpha = .5f),
                    inactiveTrackColor = Color.Gray.copy(alpha = 0.3f),
                    activeTickColor = Color.Blue,
                    inactiveTickColor = Color.Gray
                ),
                enabled = isEnabled,
                modifier = Modifier.fillMaxWidth(),
                steps = 19, // 20 positions (0, 5, 10, 15...100),
                onValueChangeFinished = {
                    println("Volume changed to: ${volume.roundToInt()}%")
                },
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