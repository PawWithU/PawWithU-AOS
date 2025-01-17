package com.kusitms.connectdog.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray5
import com.kusitms.connectdog.core.designsystem.theme.Red1

@Composable
fun ConnectDogAlertDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        content()
    }
}

@Composable
fun ConnectDogAlertDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    @StringRes titleRes: Int? = null,
    @StringRes descriptionRes: Int,
    @StringRes okText: Int,
    @StringRes cancelText: Int? = null,
    onClickOk: () -> Unit
) {
    ConnectDogAlertDialog(onDismissRequest = {}, properties = properties) {
        Column(
            modifier = modifier
                .background(color = backgroundColor, shape = RoundedCornerShape(20.dp))
                .padding(20.dp)
                .wrapContentSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Column(
                Modifier
                    .padding(vertical = 40.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (titleRes != null) {
                    Text(
                        text = stringResource(id = titleRes),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                }
                Text(
                    text = stringResource(id = descriptionRes),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = Gray2,
                    fontSize = 16.sp,
                    lineHeight = 23.sp
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ConnectDogBottomButton(
                    onClick = {
                        onClickOk()
                        onDismissRequest()
                    },
                    content = stringResource(id = okText),
                    modifier = modifier.fillMaxWidth()
                )
                if (cancelText != null) {
                    ConnectDogBottomButton(
                        onClick = onDismissRequest,
                        content = stringResource(id = cancelText),
                        enabledColor = MaterialTheme.colorScheme.surface,
                        textColor = Gray2,
                        modifier = modifier.fillMaxWidth(),
                        border = BorderStroke(width = 1.dp, color = Gray5)
                    )
                }
            }
        }
    }
}

@Composable
fun ConnectDogLogOutDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    @StringRes titleRes: Int? = null,
    @StringRes descriptionRes: Int,
    @StringRes okText: Int,
    @StringRes cancelText: Int? = null,
    onClickOk: () -> Unit
) {
    ConnectDogAlertDialog(onDismissRequest = {}, properties = properties) {
        Column(
            modifier = modifier
                .background(color = backgroundColor, shape = RoundedCornerShape(20.dp))
                .padding(20.dp)
                .wrapContentSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Column(
                Modifier
                    .padding(vertical = 40.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (titleRes != null) {
                    Text(
                        text = stringResource(id = titleRes),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                }
                Text(
                    text = stringResource(id = descriptionRes),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = Gray2,
                    fontSize = 16.sp,
                    lineHeight = 23.sp
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ConnectDogBottomButton(
                    onClick = {
                        onClickOk()
                    },
                    content = stringResource(id = okText),
                    modifier = modifier.fillMaxWidth()
                )
                if (cancelText != null) {
                    ConnectDogBottomButton(
                        onClick = onDismissRequest,
                        content = stringResource(id = cancelText),
                        enabledColor = MaterialTheme.colorScheme.surface,
                        textColor = Gray2,
                        modifier = modifier.fillMaxWidth(),
                        border = BorderStroke(width = 1.dp, color = Gray5)
                    )
                }
            }
        }
    }
}

@Composable
fun ConnectDogWithDrawDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    @StringRes titleRes: Int? = null,
    @StringRes descriptionRes: Int,
    @StringRes okText: Int,
    @StringRes cancelText: Int? = null,
    onClickOk: () -> Unit
) {
    ConnectDogAlertDialog(onDismissRequest = {}, properties = properties) {
        Column(
            modifier = modifier
                .background(color = backgroundColor, shape = RoundedCornerShape(20.dp))
                .padding(20.dp)
                .wrapContentSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Column(
                Modifier
                    .padding(vertical = 40.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (titleRes != null) {
                    Text(
                        text = stringResource(id = titleRes),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                }
                Text(
                    text = stringResource(id = descriptionRes),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = Gray2,
                    fontSize = 16.sp,
                    lineHeight = 23.sp
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ConnectDogBottomButton(
                    onClick = {
                        onClickOk()
                    },
                    content = stringResource(id = okText),
                    modifier = modifier.fillMaxWidth(),
                    enabledColor = Red1
                )
                if (cancelText != null) {
                    ConnectDogBottomButton(
                        onClick = onDismissRequest,
                        content = stringResource(id = cancelText),
                        enabledColor = MaterialTheme.colorScheme.surface,
                        textColor = Gray2,
                        modifier = modifier.fillMaxWidth(),
                        border = BorderStroke(width = 1.dp, color = Gray5)
                    )
                }
            }
        }
    }
}
