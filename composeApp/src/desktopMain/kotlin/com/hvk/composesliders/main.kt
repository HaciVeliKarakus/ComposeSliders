package com.hvk.composesliders

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        alwaysOnTop = true,
        title = "ComposeSliders",
    ) {
        App()
    }
}