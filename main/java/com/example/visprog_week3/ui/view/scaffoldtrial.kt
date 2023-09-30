package com.example.visprog_week3.ui.view

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun scaffoldView() {
    Scaffold (
        topBar = {
            AppToolBar(toolbarTitle = "Okay")
        }
    ) { paddingValues ->
        Surface (
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ){
            Column {
                Text(text = "Something")
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolBar(toolbarTitle: String) {
    TopAppBar(
        title = {
            Text(text = "$toolbarTitle")
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun scaffoldPreview() {
    scaffoldView()
}