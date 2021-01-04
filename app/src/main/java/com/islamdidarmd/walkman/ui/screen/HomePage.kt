package com.islamdidarmd.walkman.ui.screen

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun NoPermissionView() {
    Row(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "No permission granted.",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Composable
fun NoFilesView() {
    Row(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "No Files Found.",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Composable
fun Loader() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(modifier = Modifier.size(48.dp, 48.dp))
    }
}