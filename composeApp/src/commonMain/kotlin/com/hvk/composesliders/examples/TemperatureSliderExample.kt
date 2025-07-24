package com.hvk.composesliders.examples

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemperatureSliderExample() {
    var temperature by remember { mutableFloatStateOf(22f) }
    var isEnabled by remember { mutableStateOf(true) }
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Temperature: ${temperature.roundToInt()}°C (Range: 16-30)")

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Switch(
                checked = isEnabled,
                onCheckedChange = { isEnabled = it }
            )
            AnimatedVisibility(isEnabled){
                Slider(
                    value = temperature,
                    onValueChange = { newTemp -> temperature = newTemp },
                    modifier = Modifier
                        .fillMaxWidth(),
                    enabled = isEnabled,
                    onValueChangeFinished = {
                        println("Temperature set to: ${temperature.roundToInt()}°C")
                        // Apply to thermostat
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = when {
                            temperature < 20 -> Color.Blue
                            temperature < 25 -> Color.Green
                            else -> Color.Red
                        },
                        activeTrackColor = when {
                            !isEnabled -> Color.Gray
                            isEnabled && temperature < 20 -> Color.Blue.copy(alpha = 0.7f)
                            isEnabled && temperature < 25 -> Color.Green.copy(alpha = 0.7f)
                            else -> Color.Red.copy(alpha = 0.7f)
                        },
                        inactiveTrackColor = Color.Gray.copy(alpha = 0.3f)
                    ),
                    interactionSource = interactionSource,
                    steps = 13, // 14 positions (16, 17, 18...30)
                    thumb = {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    Color.White,
                                    CircleShape
                                )
                                .border(3.dp, Color.Black, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${temperature.roundToInt()}",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    },

                    track = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            Color.Blue,    // Cold (16°C)
                                            Color.Green,   // Moderate (23°C)
                                            Color.Red      // Hot (30°C)
                                        )
                                    ),
                                    shape = RoundedCornerShape(4.dp)
                                )
                        )
                    },

                    valueRange = 16f..30f
                )
            }
        }

        Text("Position: ${temperature.roundToInt() - 16 + 1}/15")

    }
}

@Composable
@Preview
private fun Private() {
    Surface {
        TemperatureSliderExample()
    }
}
