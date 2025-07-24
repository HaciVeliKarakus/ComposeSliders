package com.hvk.composesliders.examples

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

private object TemperatureSliderConstants {
    const val MIN_TEMP = 16f
    const val MAX_TEMP = 30f
    const val TEMP_STEPS = (MAX_TEMP - MIN_TEMP).toInt() // Or 13 if you want specific step count
    const val COLD_THRESHOLD = 20f
    const val MODERATE_THRESHOLD = 25f
}

@Composable
private fun getTemperatureSliderColors(
    sliderValue: Float, isEnabled: Boolean
): SliderColors {
    return SliderDefaults.colors(
        thumbColor = when {
            sliderValue < TemperatureSliderConstants.COLD_THRESHOLD -> Color.Blue
            sliderValue < TemperatureSliderConstants.MODERATE_THRESHOLD -> Color.Green
            else -> Color.Red
        }, activeTrackColor = when {
            !isEnabled -> Color.Gray
            sliderValue < TemperatureSliderConstants.COLD_THRESHOLD -> Color.Blue.copy(alpha = 0.7f)
            sliderValue < TemperatureSliderConstants.MODERATE_THRESHOLD -> Color.Green.copy(alpha = 0.7f)
            else -> Color.Red.copy(alpha = 0.7f)
        }, inactiveTrackColor = Color.Gray.copy(alpha = 0.3f)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TemperatureSliderThumb(sliderState: SliderState) {
    Box(
        modifier = Modifier.size(40.dp).background(
            color = Color.White, shape = CircleShape
        ).border(3.dp, Color.Black, CircleShape), contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${sliderState.value.roundToInt()}",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemperatureSliderExample() {
    var isEnabled by remember { mutableStateOf(true) }
    val interactionSource = remember { MutableInteractionSource() }
    val sliderState = remember {
        SliderState(
            value = TemperatureSliderConstants.MIN_TEMP,
            valueRange = TemperatureSliderConstants.MIN_TEMP..TemperatureSliderConstants.MAX_TEMP,
            steps = TemperatureSliderConstants.TEMP_STEPS
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TemperatureDisplay(sliderState = sliderState)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Switch(
                checked = isEnabled,
                onCheckedChange = { isEnabled = it },
            )
            AnimatedVisibility(isEnabled) {
                Slider(
                    state = sliderState,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isEnabled,
                    colors = getTemperatureSliderColors(sliderState.value, isEnabled),
                    interactionSource = interactionSource,
                    thumb = { TemperatureSliderThumb(sliderState) },
                    track = { TemperatureSliderTrack() },
                )
            }
        }

        SliderPositionIndicator(sliderState = sliderState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SliderPositionIndicator(sliderState: SliderState) {
    Text(
        text = "Position: ${sliderState.value.roundToInt() - TemperatureSliderConstants.MIN_TEMP.toInt() + 1}/${TemperatureSliderConstants.TEMP_STEPS + 1}",
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier.padding(top = 8.dp)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TemperatureDisplay(sliderState: SliderState) {
    Text("Temperature: ${sliderState.value.roundToInt()}°C (Range: ${TemperatureSliderConstants.MIN_TEMP.toInt()}-${TemperatureSliderConstants.MAX_TEMP.toInt()})")
}

@Composable
private fun TemperatureSliderTrack() {
    Box(
        modifier = Modifier.fillMaxWidth().height(8.dp).background(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color.Blue,    // Cold (16°C)
                    Color.Green,   // Moderate (23°C)
                    Color.Red      // Hot (30°C)
                )
            ), shape = RoundedCornerShape(4.dp)
        )
    )
}


@Composable
@Preview
private fun Private() {
    Surface {
        TemperatureSliderExample()
    }
}
