package com.islamdidarmd.walkman.util

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageAsset
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.net.toUri
import com.islamdidarmd.walkman.MainActivity
import com.islamdidarmd.walkman.data.model.PlayListItem
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.FileDescriptor

fun AppCompatActivity.fetchMusicFiles(contentState: MutableStateFlow<MainActivity.ContentState>): MutableList<PlayListItem> {
    val files = mutableListOf<PlayListItem>()

    val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.DISPLAY_NAME,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.ALBUM,
        MediaStore.Audio.Media.DURATION,
    )
    val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
    val query = contentResolver.query(
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        projection,
        selection,
        null,
        null
    )
    query?.use { cursor ->
        val idColum = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
        val songColum = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)
        val artistColum = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
        val albumColum = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
        val durationColum = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)

        while (cursor.moveToNext()) {
            val id = cursor.getLong(idColum)
            val song = cursor.getString(songColum)
            val artist = cursor.getString(artistColum)
            val album = cursor.getString(albumColum)
            val duration = cursor.getInt(durationColum)

            val contentUri =
                ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
            var rawUri: Uri? = null

            val crsr = contentResolver.query(
                contentUri,
                arrayOf(MediaStore.Images.Media.DATA),
                null,
                null,
                null
            )
            crsr?.use {
                val idx = crsr.getColumnIndex(MediaStore.Images.Media.DATA)
                crsr.moveToFirst()
                rawUri = crsr.getString(idx).toUri()
            }
            files += PlayListItem(
                song,
                artist,
                album,
                contentUri,
                rawUri,
                getAlbumart(this, id),
                duration
            )
        }

        cursor.close()
    }
    return files.also {
        contentState.value =
            if (files.isEmpty()) MainActivity.ContentState.NoFiles else MainActivity.ContentState.Content
    }
}

fun getAlbumart(context: Context, album_id: Long?): ImageBitmap? {
    var bm: Bitmap? = null
    try {
        val sArtworkUri = Uri
            .parse("content://media/external/audio/albumart")
        val uri = ContentUris.withAppendedId(sArtworkUri, album_id!!)
        val pfd: ParcelFileDescriptor? = context.contentResolver.openFileDescriptor(uri, "r")
        if (pfd != null) {
            val fd: FileDescriptor = pfd.fileDescriptor
            bm = BitmapFactory.decodeFileDescriptor(fd)
        }
    } catch (e: Exception) {
    }
    return bm?.asImageBitmap()
}