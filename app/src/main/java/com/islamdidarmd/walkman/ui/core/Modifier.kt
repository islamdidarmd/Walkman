package com.islamdidarmd.walkman.ui.core

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val rootLayoutPaddingModifier = Modifier.padding(16.dp)
val maxWidthModifier = Modifier.fillMaxWidth()
val maxHeightModifier = Modifier.fillMaxHeight()
val maxSizeModifier = Modifier.fillMaxSize()
fun Modifier.CircleShapeModifier(height: Dp, width: Dp) =
    Modifier.preferredSize(height, width).clip(CircleShape)