package com.kusitms.connectdog.feature.intermediator.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.LocationContent
import com.kusitms.connectdog.core.designsystem.component.SelectKennel
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray5
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.feature.intermediator.R
import com.kusitms.connectdog.feature.intermediator.component.CalendarBottomSheet
import com.kusitms.connectdog.feature.intermediator.component.TimeBottomSheet

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateApplicationTransportScreen(
    onBackClick: () -> Unit,
    navigateToCreateDog: () -> Unit
) {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.create_announcement,
                navigationType = TopAppBarNavigationType.BACK,
                onNavigationClick = onBackClick
            )
        },
        bottomBar = {
            BottomBar(navigateToCreateDog = navigateToCreateDog)
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

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(top = 32.dp, bottom = 112.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = stringResource(id = R.string.create_announcement_title),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Location()
        Divider(
            Modifier
                .height(8.dp)
                .fillMaxWidth(), color = Gray7)
        Spacer(modifier = Modifier.height(32.dp))
        Schedule(
            onClick = { isScheduleSheetOpen = true }
        )
        Spacer(modifier = Modifier.height(40.dp))
        Time(
            onClick = { isTimeSheetOpen = true }
        )
        Spacer(modifier = Modifier.height(40.dp))
        Kennel()
        Spacer(modifier = Modifier.height(32.dp))
        Divider(
            Modifier
                .height(8.dp)
                .fillMaxWidth(), color = Gray7)
        Spacer(modifier = Modifier.height(32.dp))
        Description()
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
            .padding(top = 40.dp, bottom = 32.dp, start = 20.dp, end = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.create_announcement_subtitle_1),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        LocationContent(
            departureLocation = "departure",
            destinationLocation = "destination"
        ) { st, end ->
//            departure = st
//            destination = end
        }
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
        Text(
            text = stringResource(id = R.string.create_announcement_subtitle_2),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogBottomButton(
            onClick = onClick,
            content = "날짜, 기간 선택",
            enabledColor = MaterialTheme.colorScheme.surface,
            textColor = Gray2,
            modifier = Modifier.align(Alignment.Start),
            border = BorderStroke(width = 1.dp, color = Gray5)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "조정 가능", fontSize = 14.sp)
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
        Text(
            text = stringResource(id = R.string.create_announcement_subtitle_3),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogBottomButton(
            onClick = onClick,
            content = "시간 선택",
            enabledColor = MaterialTheme.colorScheme.surface,
            textColor = Gray2,
            modifier = Modifier.align(Alignment.Start),
            border = BorderStroke(width = 1.dp, color = Gray5)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "조정 가능", fontSize = 14.sp)
    }
}

@Composable
private fun Kennel() {
    var hasKennel: Boolean? = null
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.create_announcement_subtitle_4),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        SelectKennel(hasKennel) { hasKennel = it }
    }
}

@Composable
private fun Description() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.create_announcement_subtitle_4),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextField(
            height = 244,
            text = "",
            onTextChanged = { "" },
            label = "느꼈던 감정, 후기를 작성해주세요",
            placeholder = ""
        )
    }
}

@Composable
private fun BottomBar(
    navigateToCreateDog: () -> Unit
) {
    ConnectDogBottomButton(
        modifier = Modifier.padding(top = 24.dp, bottom = 32.dp, start = 20.dp, end = 20.dp),
        onClick = navigateToCreateDog, content = ""
    )
}
