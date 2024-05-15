package com.kusitms.connectdog.feature.intermediator.component

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomSheet
import com.kusitms.connectdog.core.designsystem.component.ConnectDogCalendar
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.feature.intermediator.R
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarBottomSheet(
    sheetState: SheetState,
    start: LocalDate?,
    end: LocalDate?,
    onDismissClick: () -> Unit
) {
    ConnectDogBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissClick
    ) {
        Content(start = start, end = end, onDismissClick = onDismissClick)
    }
}

@Composable
private fun Content(
    start: LocalDate?,
    end: LocalDate?,
    onDismissClick: () -> Unit
) {
    var startDate: LocalDate = start ?: LocalDate.now()
    var endDate: LocalDate = end ?: LocalDate.now()

    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        ConnectDogTopAppBar(
            titleRes = R.string.create_announcement,
            navigationType = TopAppBarNavigationType.CLOSE,
            navigationIconContentDescription = "close",
            onNavigationClick = onDismissClick
        )
        Spacer(modifier = Modifier.height(30.dp))
        ConnectDogCalendar(
            startDate = startDate,
            endDate = endDate
        ) { start, end ->
            Log.d("FilterSearch", "start = $start - end = $end")
            startDate = start
            endDate = end
        }
        Spacer(modifier = Modifier.height(42.dp))
        ConnectDogBottomButton(onClick = { /*TODO*/ }, content = "적용하기")
        Spacer(modifier = Modifier.height(32.dp))
    }
}
