package com.yasserakbbach.pricetrackerapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Light theme: deep, saturated green/red for good contrast on light backgrounds
val ConnectedColorLight = Color(0xFF2E7D32)    // Material Green 800
val DisconnectedColorLight = Color(0xFFC62828)  // Material Red 800

// Dark theme: lighter, softer green/red for good contrast on dark backgrounds
val ConnectedColorDark = Color(0xFF81C784)     // Material Green 300
val DisconnectedColorDark = Color(0xFFE57373)  // Material Red 300

val connectedColor: Color
    @Composable get() = if (isSystemInDarkTheme()) ConnectedColorDark else ConnectedColorLight

val disconnectedColor: Color
    @Composable get() = if (isSystemInDarkTheme()) DisconnectedColorDark else DisconnectedColorLight