package com.kusitms.connectdog.feature.intermediator.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.feature.intermediator.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReviewScreen() {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(titleRes = R.string.review, navigationType = TopAppBarNavigationType.BACK)
        }
    ) {
        Content()
    }
}

@Composable
private fun Content() {
    Column {

    }
}