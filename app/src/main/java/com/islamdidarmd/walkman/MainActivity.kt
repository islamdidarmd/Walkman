package com.islamdidarmd.walkman

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.islamdidarmd.walkman.data.model.PlayListItem
import com.islamdidarmd.walkman.ui.core.WalkmanTheme
import com.islamdidarmd.walkman.ui.screen.AllSongs
import com.islamdidarmd.walkman.ui.screen.Loader
import com.islamdidarmd.walkman.ui.screen.NoFilesView
import com.islamdidarmd.walkman.ui.screen.NoPermissionView
import com.islamdidarmd.walkman.util.fetchMusicFiles
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                lifecycleScope.launch { musicFiles = fetchMusicFiles(contentState) }
            } else {
                contentState.value = ContentState.NoPermission
            }
        }

    private var musicFiles = mutableListOf<PlayListItem>()
    private val contentState = MutableStateFlow(ContentState.Loading)

    enum class ContentState {
        Loading,
        NoPermission,
        NoFiles,
        Content
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        handlePermission()
    }

    private fun initUI() {
        setContent {
            WalkmanTheme {
                val state = contentState.collectAsState()
                when (state.value) {
                    ContentState.Loading -> Loader()
                    ContentState.NoPermission -> NoPermissionView()
                    ContentState.NoFiles -> NoFilesView()
                    ContentState.Content -> ShowApp(musicFiles)
                }
            }
        }
    }

    private fun handlePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            lifecycleScope.launch { musicFiles = fetchMusicFiles(contentState) }
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
}

@Composable
fun ShowApp(musicFiles: MutableList<PlayListItem>) {
    AllSongs(musicFiles)
}

@Preview
@Composable
fun DefaultPreview() {
    WalkmanTheme {
        Loader()
    }
}