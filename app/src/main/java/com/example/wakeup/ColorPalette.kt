package com.example.wakeup

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun MyTheme(
    content: @Composable () -> Unit
) {
    val colors = lightColors(
        primary = Color.Green, // Modifica il colore principale
        secondary = Color.Blue, // Modifica il colore secondario
        primaryVariant = Color.DarkGray, // Modifica il colore variante principale
        secondaryVariant = Color.Gray, // Modifica il colore variante secondario
        background = Color.White, // Modifica il colore di sfondo
        surface = Color.White, // Modifica il colore di superficie
        onPrimary = Color.Black, // Modifica il colore del testo su sfondo principale
        onSecondary = Color.Black, // Modifica il colore del testo su sfondo secondario
        onBackground = Color.Black, // Modifica il colore del testo su sfondo
        onSurface = Color.Black // Modifica il colore del testo su superficie
    )

    MaterialTheme(
        colors = colors,

        content = content
    )
}

