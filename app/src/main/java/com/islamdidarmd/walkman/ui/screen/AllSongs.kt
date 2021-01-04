package com.islamdidarmd.walkman.ui.screen

import android.net.Uri
import android.text.format.DateUtils
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawShadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.islamdidarmd.walkman.R
import com.islamdidarmd.walkman.data.model.PlayListItem
import com.islamdidarmd.walkman.ui.core.typography
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.Text
import androidx.compose.ui.draw.shadow

private const val TAG = "AllSongs"

@Composable
fun AllSongs(musicFiles: MutableList<PlayListItem>) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                CreatePageTitle()
                Spacer(modifier = Modifier.preferredHeight(32.dp))
                with(this@Box) {
                    CreateAlbumArt(modifier = Modifier.align(alignment = Alignment.Center))
                }
                Spacer(modifier = Modifier.preferredHeight(32.dp))
                CreateSongsList(musicFiles)
            }
        }
    }
}

@Composable
fun CreateSongsList(songs: MutableList<PlayListItem>) {
    val selectedSongState: MutableState<PlayListItem?> = remember { mutableStateOf(null) }
    Log.d(TAG, "createSongsList: ")

    LazyColumn {
        itemsIndexed(items = songs, itemContent = { index, item ->
            CreateListItem(selectedSongState, index, songs)
        })
    }
}

@Composable
fun CreateListItem(
    selectedSongState: MutableState<PlayListItem?>,
    index: Int,
    items: MutableList<PlayListItem>
) {
    Log.d(TAG, "createListItem: ")

    val rowModifier =
        if (selectedSongState.value == items[index]) Modifier.fillMaxWidth().background(
            MaterialTheme.colors.background,
            RoundedCornerShape(8.dp)
        ).padding(16.dp)
        else Modifier.fillMaxWidth().padding(16.dp)

    Row(modifier = rowModifier) {
        Column(
            Modifier.weight(1f).align(Alignment.CenterVertically)
        ) {
            Text(
                text = items[index].songTitle,
                color = MaterialTheme.colors.onBackground,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = typography.subtitle1
            )
            //   Text(text = items[index].artist, style = typography.caption)
            //   Text(text = items[index].album, style = typography.caption)
            Text(
                text = "${items[index].rawUri}", style = typography.overline, maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = DateUtils.formatElapsedTime(items[index].duration / 1000L),
                style = typography.caption
            )
        }
        CreateActionButton(selectedSongState, index, items)
    }
}

@Composable
fun CreateActionButton(
    selectedSongState: MutableState<PlayListItem?>,
    index: Int,
    items: MutableList<PlayListItem>
) {

    val buttonColor =
        if (selectedSongState.value == items[index]) MaterialTheme.colors.primary
        else MaterialTheme.colors.surface

    FloatingActionButton(
        backgroundColor = buttonColor,
        contentColor = MaterialTheme.colors.onBackground,
        onClick = {
            if (items[index].playbackState == PlayListItem.PlaybackState.Playing) {
                items.forEachIndexed { index, playListItem ->
                    playListItem.playbackState = null
                    items[index] = playListItem
                }
                val item = items[index].copy()
                item.playbackState = PlayListItem.PlaybackState.Paused
                items[index] = item
                selectedSongState.value = item
            } else {
                items.forEachIndexed { index, playListItem ->
                    playListItem.playbackState = null
                    items[index] = playListItem
                }
                val item = items[index].copy()
                item.playbackState = PlayListItem.PlaybackState.Playing
                items[index] = item
                selectedSongState.value = item
            }
        }) {
        Icon(
            if (items[index].playbackState == PlayListItem.PlaybackState.Playing) vectorResource(id = R.drawable.ic_pause_24dp)
            else Icons.Default.PlayArrow
        )
    }
}

@Composable
fun CreatePageTitle() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = "EVOL . FUTURE",
        style = typography.caption
    )
}

@Composable
fun CreateAlbumArt(modifier: Modifier) {
    Surface(
        modifier = modifier
            .preferredSize(180.dp, 180.dp)
            .shadow(4.dp, CircleShape),
        shape = CircleShape,
    ) {
        Image(
            Icons.Default.Person,
            modifier = Modifier.border(
                1.dp,
                MaterialTheme.colors.background,
                CircleShape
            ).clipToBounds(),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun SongsListPreview() {
    AllSongs(
        musicFiles = mutableListOf(
            PlayListItem(
                songTitle = "Test Song",
                album = "Test Album",
                albumArt = null,
                artist = "Test Artist",
                duration = 100,
                uri = Uri.parse(""),
                rawUri = null
            )
        )
    )
}
