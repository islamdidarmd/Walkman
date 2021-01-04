package com.islamdidarmd.walkman.data.model

import android.net.Uri
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.graphics.ImageBitmap

data class PlayListItem(
    val songTitle: String,
    val artist: String,
    val album: String,
    val uri: Uri,
    val rawUri: Uri?,
    val albumArt: ImageBitmap?,
    val duration: Int,
    var playbackState: PlaybackState? = null
) {
    enum class PlaybackState {
        Playing,
        Paused
    }
}