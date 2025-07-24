package com.hvk.composesliders.examples

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
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
        thumbColor = getThumbColor(sliderValue),
        activeTrackColor = getActiveTrackColor(sliderValue, isEnabled),
        inactiveTrackColor = Color.Gray.copy(alpha = 0.3f)
    )
}

@Composable
private fun getThumbColor(sliderValue: Float): Color = when {
    sliderValue < TemperatureSliderConstants.COLD_THRESHOLD -> Color.Blue
    sliderValue < TemperatureSliderConstants.MODERATE_THRESHOLD -> Color.Yellow
    else -> Color.Red
}

@Composable
private fun getActiveTrackColor(
    sliderValue: Float,
    isEnabled: Boolean
): Color = if (!isEnabled) Color.Gray else getThumbColor(sliderValue).copy(alpha = 0.7f)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TemperatureSliderThumb(
    sliderState: SliderState,
    interactionSource: InteractionSource
) {
    val isDragging = interactionSource.collectIsDraggedAsState().value
    val size by animateDpAsState(
        targetValue = if (isDragging) 50.dp else 40.dp,
        label = "Thumb size animation"
    )

    Box(
        modifier = Modifier.size(size)
            .background(
                color = Color.White,
                shape = CircleShape
            ).border(
                width = 3.dp,
                color = Color.Black,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
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
                    thumb = { TemperatureSliderThumb(sliderState, interactionSource) },
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
    val currentStep =
        sliderState.value.roundToInt() - TemperatureSliderConstants.MIN_TEMP.toInt() + 1
    val totalSteps = TemperatureSliderConstants.TEMP_STEPS + 1

    Text(
        text = "Position: $currentStep/$totalSteps",
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier.padding(top = 8.dp)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TemperatureDisplay(sliderState: SliderState) {
    val currentTemperature = sliderState.value.roundToInt()
    val minTemperature = TemperatureSliderConstants.MIN_TEMP.toInt()
    val maxTemperature = TemperatureSliderConstants.MAX_TEMP.toInt()

    Text("Temperature: $currentTemperature°C (Range: $minTemperature-$maxTemperature°C)")
}

@Composable
private fun TemperatureSliderTrack() {
    Box(
        modifier = Modifier.fillMaxWidth().height(8.dp).background(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color.Blue,    // Cold (16°C)
                    Color.Yellow,   // Moderate (23°C)
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
