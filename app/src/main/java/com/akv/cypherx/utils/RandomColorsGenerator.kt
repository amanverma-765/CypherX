package com.akv.cypherx.utils

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun generateRandomColor(isDarkBackground: Boolean): Color {

    val r = Random.nextInt(256)
    val g = Random.nextInt(256)
    val b = Random.nextInt(256)

    val luminance = 0.2126 * r + 0.7152 * g + 0.0722 * b

    if (isDarkBackground) {
        if (luminance > 128) {
            return generateRandomColor(isDarkBackground)
        }
    } else {
        if (luminance <= 128) {
            return generateRandomColor(isDarkBackground)
        }
    }

    return Color(r, g, b)
}