package com.islamdidarmd.walkman

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.ui.tooling.preview.Preview
import com.islamdidarmd.walkman.ui.core.WalkmanTheme
import com.islamdidarmd.walkman.ui.screen.AllSongs

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WalkmanTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    AllSongs()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WalkmanTheme {
        App()
    }
}