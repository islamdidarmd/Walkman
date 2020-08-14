package com.islamdidarmd.walkman.ui.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ColumnScope.gravity
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.islamdidarmd.walkman.R
import com.islamdidarmd.walkman.ui.core.*

@Composable
fun AllSongs() {
    Surface(modifier = maxSizeModifier) {
        Box(modifier = rootLayoutPaddingModifier) {
            Column(modifier = maxWidthModifier.padding(top = 16.dp)) {
                createPageTitle()
                Spacer(modifier = Modifier.preferredHeight(32.dp))
                createAlbumArt()
                Spacer(modifier = Modifier.preferredHeight(32.dp))
                createSongsList()
            }
        }
    }
}

@Composable
fun createSongsList() {
    val selectedSong = remember { mutableStateOf(-1) }

    val list = mutableListOf<String>("A", "B", "C")
    LazyColumnForIndexed(items = list) { index, item ->

        val rowModifier =
            if (selectedSong.value == index) maxWidthModifier.background(
                MaterialTheme.colors.background,
                RoundedCornerShape(8.dp)
            )
                .padding(8.dp)
            else maxWidthModifier.padding(8.dp)

        Row(modifier = rowModifier) {
            Column(
                Modifier.weight(1f).gravity(Alignment.CenterVertically)
            ) {
                Text(text = item, style = typography.subtitle1)
                Text(text = item, style = typography.caption)
            }

            if (selectedSong.value == index) createPauseButton(selectedSong, index)
            else createPlayButton(selectedSong, index)
        }
    }
}

@Composable
fun createPauseButton(selectedSong: MutableState<Int>, index: Int) {
    val contentModifier = Modifier
        .preferredSize(48.dp, 48.dp)
        .drawShadow(4.dp, CircleShape)
        .clickable(onClick = {
            selectedSong.value = -1
        })
    Surface(
        color = MaterialTheme.colors.primary,
        shape = CircleShape,
        modifier = contentModifier
    ) {
        Image(
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
            modifier = Modifier.size(20.dp, 20.dp).gravity(Alignment.End),
            asset = vectorResource(id = R.drawable.ic_pause_24dp)
        )
    }
}

@Composable
fun createPlayButton(selectedSong: MutableState<Int>, index: Int) {
    val contentModifier = Modifier
        .preferredSize(48.dp, 48.dp)
        .drawShadow(4.dp, CircleShape)
        .clickable(onClick = {
            selectedSong.value = index
        })
    Surface(
        shape = CircleShape,
        border = Border(1.dp, MaterialTheme.colors.background),
        modifier = contentModifier
    ) {
        Image(
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
            modifier = Modifier.size(20.dp, 20.dp).gravity(Alignment.End),
            asset = Icons.Default.PlayArrow
        )
    }
}

@Composable
fun createPageTitle() {
    Text(
        modifier = maxWidthModifier,
        textAlign = TextAlign.Center,
        text = "EVOL . FUTURE",
        style = typography.caption
    )
}

@Composable
fun createAlbumArt() {
    Surface(
        modifier = Modifier
            .gravity(Alignment.CenterHorizontally)
            .preferredSize(180.dp, 180.dp)
            .drawShadow(4.dp, CircleShape),
        shape = CircleShape,
    ) {
        Image(
            modifier = Modifier.drawBorder(
                1.dp,
                MaterialTheme.colors.background,
                CircleShape
            ).clipToBounds(),
            asset = vectorResource(id = R.drawable.ic_launcher_background),
            contentScale = ContentScale.Crop
        )
    }
}
