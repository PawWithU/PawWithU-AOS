package com.kusitms.connectdog.feature.intermediator.screen

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.Detail
import com.kusitms.connectdog.core.designsystem.component.SelectDogSize
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.core.designsystem.theme.Orange20
import com.kusitms.connectdog.feature.intermediator.R
import com.kusitms.connectdog.feature.intermediator.viewmodel.CreateApplicationViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateApplicationDogScreen(
    imeHeight: Int,
    viewModel: CreateApplicationViewModel,
    onBackClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    Scaffold(
        modifier = Modifier
            .clickable(
                onClick = { focusManager.clearFocus() },
                indication = null,
                interactionSource = interactionSource
            ),
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.create_announcement,
                navigationType = TopAppBarNavigationType.BACK,
                onNavigationClick = onBackClick
            )
        },
        bottomBar = {
            ConnectDogBottomButton(
                modifier = Modifier.background(color = Color.White).padding(horizontal = 20.dp, vertical = 24.dp),
                onClick = { },
                content = "등록 완료"
            )
        }
    ) {
        Content(
            viewModel = viewModel,
            imeHeight = imeHeight
        )
    }
}

@Composable
private fun Content(
    viewModel: CreateApplicationViewModel,
    imeHeight: Int
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(top = 32.dp)
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
        Spacer(modifier = Modifier.height(40.dp))
        Name(
            name = viewModel.name,
            updateName = viewModel::updateName
        )
        Spacer(modifier = Modifier.height(40.dp))
        Size()
        Spacer(modifier = Modifier.height(32.dp))
        Divider(thickness = 8.dp, color = Gray7)
        Spacer(modifier = Modifier.height(32.dp))
        Image()
        Spacer(modifier = Modifier.height(32.dp))
        Divider(thickness = 8.dp, color = Gray7)
        Spacer(modifier = Modifier.height(32.dp))
        Significant(viewModel = viewModel, imeHeight = imeHeight, scrollState = scrollState)
    }
}

@Composable
private fun Name(
    name: String,
    updateName: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.create_announcement_dog_subtitle_1),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Gray1
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextField(
            text = name,
            onTextChanged = updateName,
            label = "이름",
            placeholder = "이름 입력"
        )
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Orange20, shape = RoundedCornerShape(4.dp))
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.create_announcement_dog_comment),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun Size() {
    val detail = Detail()
    var dogSize = detail.dogSize

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.create_announcement_dog_subtitle_2),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Gray1
        )
        Spacer(modifier = Modifier.height(12.dp))
        SelectDogSize(dogSize) {
            dogSize = it
        }
    }
}

@Composable
private fun Image(
    viewModel: CreateApplicationViewModel = hiltViewModel()
) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) {
            it.forEach { uri ->
                viewModel.updateUriList(uri)
            }
        }
    val uriList by viewModel.uriList.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Row {
            Text(
                text = stringResource(id = R.string.create_announcement_dog_subtitle_3),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Gray1
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${uriList.size}/5",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Gray3
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow {
            items(uriList.size + 1) { index ->
                key(index) {
                    if (index < uriList.size) {
                        Photo(
                            uri = uriList[index],
                            onRemoveClick = { viewModel.removeUriList(uriList[index]) }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    } else {
                        AddPhotoButton {
                            launcher.launch(
                                PickVisualMediaRequest(
                                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Photo(
    uri: Uri,
    onRemoveClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .border(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.outline,
                width = 1.dp
            )
    ) {
        AsyncImage(
            model = uri,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        IconButton(
            onClick = onRemoveClick,
            modifier = Modifier
                .size(18.dp)
                .padding(top = 5.dp, end = 5.dp)
                .align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Outlined.Close,
                tint = Color.White,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun AddPhotoButton(onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.clickable { onClick() }
    ) {
        val stroke = Stroke(
            width = 2f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        )
        Canvas(Modifier.size(80.dp)) {
            drawRoundRect(
                color = Gray4,
                style = stroke,
                topLeft = Offset(0f, 0f),
                cornerRadius = CornerRadius(6.dp.toPx(), 6.dp.toPx())
            )
        }
        Icon(
            imageVector = Icons.Outlined.Add,
            tint = Gray4,
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun Significant(
    scrollState: ScrollState,
    imeHeight: Int,
    viewModel: CreateApplicationViewModel
) {
    LaunchedEffect(imeHeight) {
        if (imeHeight != 0) scrollState.animateScrollTo(scrollState.maxValue)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.create_announcement_dog_subtitle_4),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Gray1
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextField(
            text = viewModel.specifics,
            onTextChanged = viewModel::updateSpecifics,
            label = "",
            placeholder = "",
            height = 244
        )
        Spacer(modifier = Modifier.height((imeHeight).dp))
    }
}
