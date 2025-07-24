package com.hvk.composesliders

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform