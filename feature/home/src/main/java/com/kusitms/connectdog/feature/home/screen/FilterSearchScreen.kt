package com.kusitms.connectdog.feature.home.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogCard
import com.kusitms.connectdog.core.designsystem.component.ConnectDogExpandableCard
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.feature.home.R

@Composable
internal fun FilterSearchScreen() {
    Column {
        TopAppBar()
        Spacer(modifier = Modifier.size(14.dp))
        LocationCard()
    }
}

@Composable
private fun TopAppBar() {
    ConnectDogTopAppBar(
        titleRes = R.string.filter_app_bar_title,
        navigationType = TopAppBarNavigationType.CLOSE,
        navigationIconContentDescription = "닫기"
    )
}

@Composable
private fun LocationCard() {
    ConnectDogExpandableCard(
        modifier = Modifier.fillMaxWidth(),
        defaultContent = {
            DefaultCardContent(titleRes = R.string.filter_location, content = null)
        },
        expandedContent = {
            ExpandedCardContent(
                titleRes = R.string.filter_location,
                spacer = 40,
                onClickSkip = { /*TODO*/ },
                onClickNext = {}
            ) { LocationContent() }
        }
    )
}

@Composable
private fun DefaultCardContent(
    @StringRes titleRes: Int,
    content: String?
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 57.dp)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 14.sp
        )
        Text(
            text = content ?: stringResource(id = R.string.filter_choose),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 14.sp,
            color = Gray3
        )
    }
}

@Composable
private fun ExpandedCardContent(
    @StringRes titleRes: Int,
    spacer: Int,
    onClickSkip: () -> Unit,
    onClickNext: () -> Unit,
    content: @Composable () -> Unit,
) {
    Column(modifier = Modifier.padding(20.dp)) {
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.titleLarge,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.size(spacer.dp))
        content()
        Spacer(modifier = Modifier.size(spacer.dp))
        DialogBottomButton(onClickSkip = onClickSkip, onClickNext = onClickNext)
    }
}

@Composable
private fun LocationContent() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.img_location_path),
            contentDescription = "출발지-도착지"
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column {
            SelectLocation(
                titleRes = R.string.filter_departure,
                place = null,
                placeholderRes = R.string.filter_select_departure
            )
            Spacer(modifier = Modifier.size(30.dp))
            SelectLocation(
                titleRes = R.string.filter_destination,
                place = null,
                placeholderRes = R.string.filter_select_destination
            )
        }
    }
}

@Composable
private fun SelectLocation(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    place: String?,
    @StringRes placeholderRes: Int
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = place ?: stringResource(id = placeholderRes),
            style = MaterialTheme.typography.bodyLarge,
            color = Gray4,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Divider(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun DialogBottomButton(
    onClickSkip: () -> Unit,
    onClickNext: () -> Unit,
){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.filter_skip),
            style = MaterialTheme.typography.titleSmall,
            fontSize = 14.sp,
            modifier = Modifier.clickable { onClickSkip() }
        )
        Button(
            onClick = onClickNext,
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.size(width = 104.dp, height = 37.dp)
        ) {
            Text(
                text = stringResource(id = R.string.filter_next),
                style = MaterialTheme.typography.titleSmall,
                fontSize = 14.sp,
            )
        }
    }
}

@Preview
@Composable
private fun FilterSearchScreenPreview() {
    FilterSearchScreen()
}