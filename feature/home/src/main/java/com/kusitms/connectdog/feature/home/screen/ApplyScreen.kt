package com.kusitms.connectdog.feature.home.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray5
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.core.designsystem.theme.Orange20
import com.kusitms.connectdog.feature.home.ApplyViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ApplyScreen(
    onBackClick: () -> Unit = {},
    onClick: () -> Unit = {},
    postId: Long,
    imeHeight: Int,
    viewModel: ApplyViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.apply_volunter,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick
            )
        }
    ) {
        Content(viewModel, postId, imeHeight, onClick, onBackClick)
    }
}

@Composable
private fun Content(
    viewModel: ApplyViewModel,
    postId: Long,
    imeHeight: Int,
    onClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isChecked by viewModel.isChecked.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 80.dp)
            .verticalScroll(scrollState)
            .fillMaxHeight()
            .clickable(
                onClick = { focusManager.clearFocus() },
                indication = null,
                interactionSource = interactionSource
            )
    ) {
        LaunchedEffect(imeHeight) {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
        Text(
            text = "이동봉사 모집자에게\n전달할 정보를 입력해주세요",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        BasicInformation(isChecked) {
            viewModel.updateIsChecked()
        }
        Spacer(modifier = Modifier.height(20.dp))
        ConnectDogTextField(
            text = viewModel.name,
            enabled = !isChecked,
            label = "이름",
            placeholder = "이름 입력",
            keyboardType = KeyboardType.Text,
            onTextChanged = { viewModel.updateName(it) },
            isError = viewModel.isAvailableName == false
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextField(
            enabled = !isChecked,
            text = viewModel.phoneNumber,
            label = "휴대폰 번호",
            placeholder = "\'-\' 빼고 입력",
            keyboardType = KeyboardType.Number,
            onTextChanged = { if (it.length <= 11) viewModel.updatePhoneNumber(it) }
        )
        Spacer(modifier = Modifier.height(20.dp))
        NoticeCard()
        Spacer(modifier = Modifier.height(32.dp))
        Divider(
            Modifier
                .height(8.dp)
                .fillMaxWidth(),
            color = Gray7
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "전달 및 문의사항",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextField(
            text = viewModel.content,
            label = "",
            placeholder = "모집자에게 전달 및 문의 필요한 사항이 있다면\n입력해주세요. ",
            keyboardType = KeyboardType.Text,
            height = 180,
            onTextChanged = { viewModel.updateContent(it) }
        )
        Spacer(modifier = Modifier.height(20.dp))
        ConnectDogBottomButton(
            enabled = viewModel.name.isNotEmpty() && viewModel.phoneNumber.isNotEmpty(),
            content = "신청하기",
            onClick = {
                viewModel.postApplyVolunteer(postId)
                onBackClick()
                onClick()
            }
        )
        Spacer(modifier = Modifier.height((imeHeight + 20).dp))
    }
}

@Composable
fun NoticeCard() {
    Card(
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = Orange20),
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {
        Text(
            text = "이동봉사 모집자 측에서 해당 휴대폰 번호로 연락드릴 예정입니다.",
            color = Gray2,
            fontSize = 12.sp,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
                .padding(horizontal = 16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BasicInformation(
    isChecked: Boolean,
    updateIsChecked: () -> Unit
) {
    Row(
        modifier = Modifier.padding(horizontal = 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            Checkbox(
                checked = isChecked,
                colors = CheckboxDefaults.colors(
                    uncheckedColor = Gray5
                ),
                onCheckedChange = { updateIsChecked() }
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "기본정보 불러오기",
            color = Gray2,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        )
    }
}

@Preview
@Composable
private fun ApplyScreenPreview() {
    ConnectDogTheme {
        ApplyScreen(
            postId = 1,
            imeHeight = 200,
            viewModel = hiltViewModel()
        )
    }
}
