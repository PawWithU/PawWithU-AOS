package com.kusitms.connectdog.feature.intermediator.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomSheet
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.LazyListSnapperLayoutInfo
import dev.chrisbanes.snapper.rememberLazyListSnapperLayoutInfo
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import java.lang.Math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeBottomSheet(
    sheetState: SheetState,
    onDismissClick: () -> Unit
) {
    val hour = arrayListOf<Int>().apply {
        repeat(12) { add(it) }
    }

    val minute = arrayListOf<Int>().apply {
        repeat(60) { add(it) }
    }

    val time = arrayListOf<Int>().apply {
        repeat(2) { add(it) }
    }

    ConnectDogBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissClick
    ) {
        WheelPicker(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 20.dp),
            pickerMaxHeight = 212.dp,
            defaultTextStyle = MaterialTheme.typography.bodyMedium,
            centerTextStyle = MaterialTheme.typography.bodyLarge,
            time = time,
            hour = hour,
            minute = minute
        )
    }
}

@OptIn(ExperimentalSnapperApi::class)
@Composable
fun WheelPicker(
    modifier: Modifier,
    pickerMaxHeight: Dp = 250.dp,
    timeIndex: Int? = null,
    hourIndex: Int? = null,
    minuteIndex: Int? = null,
    time: ArrayList<Int>,
    hour: ArrayList<Int>,
    minute: ArrayList<Int>,
    defaultTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    centerTextStyle: TextStyle = TextStyle(fontSize = 23.sp, fontWeight = FontWeight.Bold),
    defaultTextColor: Color = Color.Black.copy(alpha = 0.4f),
    centerTextColor: Color = Color.Black.copy(alpha = 1.0f),
    selectedBackgroundColor: Color = Gray7
) {
    val timeState = rememberLazyListState(initialFirstVisibleItemIndex = (Int.MAX_VALUE / 2.0).toInt() - 6)
    val timeLayoutInfo: LazyListSnapperLayoutInfo = rememberLazyListSnapperLayoutInfo(timeState) // wheel picker 의 layout 정보
    var timeCurrentIndex: Int?

    val hourState = rememberLazyListState(initialFirstVisibleItemIndex = (Int.MAX_VALUE / 2.0).toInt() - 5)
    val hourLayoutInfo: LazyListSnapperLayoutInfo = rememberLazyListSnapperLayoutInfo(hourState) // wheel picker 의 layout 정보
    var hourCurrentIndex: Int?

    val minuteState = rememberLazyListState(initialFirstVisibleItemIndex = (Int.MAX_VALUE / 2.0).toInt() - 15)
    val minuteLayoutInfo: LazyListSnapperLayoutInfo = rememberLazyListSnapperLayoutInfo(minuteState) // wheel picker 의 layout 정보
    var minuteCurrentIndex: Int?

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 90.dp)
        ) {
            LazyColumn(
                modifier = Modifier.height(pickerMaxHeight).weight(1f),
                state = timeState,
                flingBehavior = rememberSnapperFlingBehavior(timeState),
                verticalArrangement = Arrangement.Center
            ) {
                items(
                    count = time.size,
                    key = { it }
                ) {
                    val index = it % time.size

                    timeCurrentIndex = timeLayoutInfo.currentItem?.index?.plus(1)?.rem(time.size)

                    if (timeCurrentIndex != null && timeCurrentIndex!! < 0) {
                        timeCurrentIndex = timeCurrentIndex!! + time.size
                    }

                    val curTextIsCenter = timeCurrentIndex == time[index]
                    val curTextIsCenterDiffer1 = timeCurrentIndex == abs(time[index] - 1) || timeCurrentIndex == abs(time[index] + 1)

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(pickerMaxHeight * 0.15f)
                            .padding(vertical = 5.dp)
                            .scale(
                                scaleX = if (curTextIsCenter) 1.4f else 1.2f,
                                scaleY = if (curTextIsCenter) 1.4f else 1.2f
                            ),
                        text = time[index].toString(),
                        style = if (curTextIsCenter) centerTextStyle else defaultTextStyle,
                        color = if (curTextIsCenter) centerTextColor else Gray2,
                        textAlign = TextAlign.Center
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.height(pickerMaxHeight).weight(1f),
                state = hourState,
                flingBehavior = rememberSnapperFlingBehavior(hourState),
                verticalArrangement = Arrangement.Center
            ) {
                items(
                    count = Int.MAX_VALUE,
                    key = { it }
                ) {
                    val index = it % hour.size

                    hourCurrentIndex = hourLayoutInfo.currentItem?.index?.minus(Int.MAX_VALUE / 2)?.plus(3)?.rem(hour.size)

                    if (hourCurrentIndex != null && hourCurrentIndex!! < 0) {
                        hourCurrentIndex = hourCurrentIndex!! + hour.size
                    }

                    val curTextIsCenter = hourCurrentIndex == hour[index]
                    val curTextIsCenterDiffer1 = hourCurrentIndex == abs(hour[index] - 1) || hourCurrentIndex == abs(hour[index] + 1)
                    val curTextIsCenterDiffer2 = hourCurrentIndex == abs(hour[index] - 2) || hourCurrentIndex == abs(hour[index] + 2)

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(pickerMaxHeight * 0.15f)
                            .padding(vertical = 5.dp)
                            .scale(
                                scaleX = if (curTextIsCenter) 1.4f else if (curTextIsCenterDiffer1) 1.2f else if (curTextIsCenterDiffer2) 1f else 0.8f,
                                scaleY = if (curTextIsCenter) 1.4f else if (curTextIsCenterDiffer1) 1.2f else if (curTextIsCenterDiffer2) 1f else 0.8f
                            ),
                        text = hour[index].toString(),
                        style = if (curTextIsCenter) centerTextStyle else defaultTextStyle,
                        color = if (curTextIsCenter) centerTextColor else if (curTextIsCenterDiffer1) Gray2 else if (curTextIsCenterDiffer2) Gray3 else Gray4,
                        textAlign = TextAlign.Center
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.height(pickerMaxHeight).weight(1f),
                state = minuteState,
                flingBehavior = rememberSnapperFlingBehavior(minuteState),
                verticalArrangement = Arrangement.Center
            ) {
                items(
                    count = Int.MAX_VALUE,
                    key = { it }
                ) {
                    val index = it % minute.size

                    minuteCurrentIndex = minuteLayoutInfo.currentItem?.index?.minus(Int.MAX_VALUE / 2)?.plus(23)?.rem(minute.size)

                    if (minuteCurrentIndex != null && minuteCurrentIndex!! < 0) {
                        minuteCurrentIndex = minuteCurrentIndex!! + minute.size
                    }

                    val curTextIsCenter = minuteCurrentIndex == minute[index]
                    val curTextIsCenterDiffer1 = minuteCurrentIndex == abs(minute[index] - 1) || minuteCurrentIndex == abs(minute[index] + 1)
                    val curTextIsCenterDiffer2 = minuteCurrentIndex == abs(minute[index] - 2) || minuteCurrentIndex == abs(minute[index] + 2)

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(pickerMaxHeight * 0.15f)
                            .padding(vertical = 5.dp)
                            .scale(
                                scaleX = if (curTextIsCenter) 1.4f else if (curTextIsCenterDiffer1) 1.2f else if (curTextIsCenterDiffer2) 1f else 0.8f,
                                scaleY = if (curTextIsCenter) 1.4f else if (curTextIsCenterDiffer1) 1.2f else if (curTextIsCenterDiffer2) 1f else 0.8f
                            ),
                        text = minute[index].toString(),
                        style = if (curTextIsCenter) centerTextStyle else defaultTextStyle,
                        color = if (curTextIsCenter) centerTextColor else if (curTextIsCenterDiffer1) Gray2 else if (curTextIsCenterDiffer2) Gray3 else Gray4,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(pickerMaxHeight * 0.15f)
                .clip(RoundedCornerShape(15.dp))
                .background(selectedBackgroundColor)
                .zIndex(-1f)
        )

        LaunchedEffect(Unit) {
            timeIndex?.let {
                timeState.scrollToItem(
                    index = (Int.MAX_VALUE / 2.0).toInt() - 25 + it,
                    scrollOffset = timeState.layoutInfo.visibleItemsInfo[0].size / 5
                )
            } ?: kotlin.run {
                timeState.scrollBy(
                    value = timeState.layoutInfo.visibleItemsInfo[0].size.toFloat() / 5
                )
            }
        }

        LaunchedEffect(Unit) {
            hourIndex?.let {
                hourState.scrollToItem(
                    index = (Int.MAX_VALUE / 2.0).toInt() - 25 + it,
                    scrollOffset = hourState.layoutInfo.visibleItemsInfo[0].size / 5
                )
            } ?: kotlin.run {
                hourState.scrollBy(
                    value = hourState.layoutInfo.visibleItemsInfo[0].size.toFloat() / 5
                )
            }
        }

        LaunchedEffect(Unit) {
            minuteIndex?.let {
                minuteState.scrollToItem(
                    index = (Int.MAX_VALUE / 2.0).toInt() - 25 + it,
                    scrollOffset = minuteState.layoutInfo.visibleItemsInfo[0].size / 5
                )
            } ?: kotlin.run {
                minuteState.scrollBy(
                    value = minuteState.layoutInfo.visibleItemsInfo[0].size.toFloat() / 5
                )
            }
        }
    }
}
