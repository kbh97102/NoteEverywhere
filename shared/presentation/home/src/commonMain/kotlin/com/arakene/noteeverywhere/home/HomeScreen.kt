package com.arakene.noteeverywhere.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arakene.noteeverywhere.designsystem.NoteTheme

@Composable
fun HomeScreen() {
    NoteTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text("noteEveryWhere")
        }
    }
}
