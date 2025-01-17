package com.kusitms.connectdog.feature.management.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.component.ApplicationBottomSheet
import com.kusitms.connectdog.core.designsystem.component.ConnectDogAlertDialog
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.model.Application
import com.kusitms.connectdog.core.model.Volunteer
import com.kusitms.connectdog.feature.management.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MyApplicationBottomSheet(
    application: Application,
    sheetState: SheetState,
    volunteer: Volunteer,
    onDismissRequest: () -> Unit,
    onDeleteClick: (Long) -> Unit
) {
    var isCancelDialogVisible by remember { mutableStateOf(false) }

    ApplicationBottomSheet(
        titleRes = R.string.check_my_appliance,
        application = application,
        volunteer = volunteer,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        ConnectDogBottomButton(
            onClick = { isCancelDialogVisible = true },
            content = stringResource(id = R.string.cancel_appliance),
            textColor = MaterialTheme.colorScheme.error,
            enabledColor = MaterialTheme.colorScheme.surface,
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.error),
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 52.dp)
        )
    }

    if (isCancelDialogVisible) {
        CancelDialog(onDismiss = { isCancelDialogVisible = false }) {
            onDeleteClick(application.applicationId!!)
            onDismissRequest()
        }
    }
}

@Composable
private fun CancelDialog(
    onDismiss: () -> Unit,
    onOkClick: () -> Unit
) {
    ConnectDogAlertDialog(
        onDismissRequest = onDismiss,
        titleRes = R.string.question_cancel,
        descriptionRes = R.string.question_cancel_description,
        okText = R.string.ok_cancel,
        cancelText = R.string.cancel_back
    ) {
        onOkClick()
    }
}
