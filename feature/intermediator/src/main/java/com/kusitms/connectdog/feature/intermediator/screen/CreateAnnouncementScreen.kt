package com.kusitms.connectdog.feature.intermediator.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray5
import com.kusitms.connectdog.feature.intermediator.R
import com.kusitms.connectdog.feature.intermediator.component.CalendarBottomSheet
import com.kusitms.connectdog.feature.intermediator.component.TimeBottomSheet

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateAnnouncementScreen() {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.create_announcement,
                navigationType = TopAppBarNavigationType.BACK
            )
        }
    ) {
        Content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content() {
    var isScheduleSheetOpen by rememberSaveable { mutableStateOf(false) }
    var isTimeSheetOpen by rememberSaveable { mutableStateOf(false) }

    val scheduleSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val timeSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Column(
        modifier = Modifier
            .padding(vertical = 32.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(text = stringResource(id = R.string.create_announcement_title))
        Spacer(modifier = Modifier.height(40.dp))
        Schedule(
            onClick = { isScheduleSheetOpen = true }
        )
        Time(
            onClick = { isTimeSheetOpen = true }
        )
    }

    if (isScheduleSheetOpen) {
        CalendarBottomSheet(
            sheetState = scheduleSheetState,
            start = null,
            end = null,
            onDismissClick = { isScheduleSheetOpen = false }
        )
    }

    if (isTimeSheetOpen) {
        TimeBottomSheet(
            sheetState = timeSheetState,
            onDismissClick = { isScheduleSheetOpen = false }
        )
    }
}

@Composable
private fun Location() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(text = stringResource(id = R.string.create_announcement_subtitle_1))
    }
}

@Composable
private fun Schedule(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(text = stringResource(id = R.string.create_announcement_subtitle_2))
        ConnectDogBottomButton(
            onClick = onClick,
            content = "날짜, 기간 선택",
            enabledColor = MaterialTheme.colorScheme.surface,
            textColor = Gray2,
            modifier = Modifier.align(Alignment.Start),
            border = BorderStroke(width = 1.dp, color = Gray5)
        )
    }
}

@Composable
private fun Time(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        ConnectDogBottomButton(
            onClick = onClick,
            content = "시간 선택",
            enabledColor = MaterialTheme.colorScheme.surface,
            textColor = Gray2,
            modifier = Modifier.align(Alignment.Start),
            border = BorderStroke(width = 1.dp, color = Gray5)
        )
    }
}

@Composable
private fun Kennel() {
}

@Composable
private fun Description() {
}

@Preview
@Composable
private fun CreateAnnouncementScreenPreview() {
    ConnectDogTheme {
        CreateAnnouncementScreen()
    }
}
