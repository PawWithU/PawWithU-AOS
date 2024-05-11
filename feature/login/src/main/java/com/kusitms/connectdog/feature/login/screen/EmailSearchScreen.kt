package com.kusitms.connectdog.feature.login.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextFieldWithButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Orange_40
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.feature.login.R
import com.kusitms.connectdog.feature.login.viewmodel.SearchViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EmailSearchScreen(
    imeHeight: Int,
    onBackClick: () -> Unit,
    navigateToCompleteScreen: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.email_search,
                navigationType = TopAppBarNavigationType.BACK,
                onNavigationClick = onBackClick
            )
        }
    ) {
        Content(
            imeHeight = imeHeight,
            navigateToCompleteScreen = navigateToCompleteScreen,
            viewModel = viewModel
        )
    }
}

@Composable
private fun Content(
    imeHeight: Int,
    navigateToCompleteScreen: () -> Unit,
    viewModel: SearchViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = "휴대폰 번호 인증을\n진행해주세요",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(40.dp))
        ConnectDogTextFieldWithButton(
            text = viewModel.phoneNumber,
            width = 62,
            height = 27,
            textFieldLabel = "휴대폰 번호",
            placeholder = "- 빼고 입력",
            buttonLabel = "인증 요청",
            onTextChanged = {
                viewModel.updatePhoneNumber(it)
//                viewModel.updateEmailValidity()
            },
            onClick = {
            },
            padding = 5
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextFieldWithButton(
            text = viewModel.phoneNumber,
            width = 62,
            height = 27,
            textFieldLabel = "인증번호",
            placeholder = "인증번호 4자리",
            buttonLabel = "인증 확인",
            onTextChanged = {
                viewModel.updatePhoneNumber(it)
//                viewModel.updateEmailValidity()
            },
            onClick = {
            },
            padding = 5
        )
        Spacer(modifier = Modifier.weight(1f))
        ConnectDogNormalButton(
            content = "다음",
            color = if (true) { PetOrange } else { Orange_40 },
            onClick = navigateToCompleteScreen,
            modifier =
            Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
        Spacer(modifier = Modifier.height((imeHeight + 32).dp))
    }
}
