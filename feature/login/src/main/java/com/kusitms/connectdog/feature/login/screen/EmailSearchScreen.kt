package com.kusitms.connectdog.feature.login.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextFieldWithButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.login.R
import com.kusitms.connectdog.feature.login.viewmodel.SearchViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EmailSearchScreen(
    imeHeight: Int,
    onBackClick: () -> Unit,
    navigateToCompleteScreen: (String) -> Unit,
    onSendMessageClick: (String) -> Unit,
    onVerifyCodeClick: (String, (Boolean) -> Unit) -> Unit,
    userType: UserType,
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
            onSendMessageClick = onSendMessageClick,
            onVerifyCodeClick = onVerifyCodeClick,
            viewModel = viewModel,
            userType = userType
        )
    }
}

@Composable
private fun Content(
    imeHeight: Int,
    navigateToCompleteScreen: (String) -> Unit,
    onSendMessageClick: (String) -> Unit,
    onVerifyCodeClick: (String, (Boolean) -> Unit) -> Unit,
    userType: UserType,
    viewModel: SearchViewModel
) {
    val context = LocalContext.current
    val isSendNumber by remember { viewModel.isSendNumber }.collectAsState()
    val isCertified by remember { viewModel.isCertified }.collectAsState()

    LaunchedEffect(key1 = viewModel) {
        viewModel.findEmail.collect {
            it?.let { navigateToCompleteScreen(it) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = "휴대폰 번호 인증을\n진행해주세요",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 25.sp
        )
        Spacer(modifier = Modifier.height(40.dp))
        ConnectDogTextFieldWithButton(
            text = viewModel.phoneNumber,
            width = 62,
            height = 27,
            textFieldLabel = "휴대폰 번호",
            keyboardType = KeyboardType.Number,
            placeholder = "- 빼고 입력",
            buttonLabel = "인증 요청",
            onTextChanged = { if (it.length <= 11) viewModel.updatePhoneNumber(it) },
            onClick = {
                if (viewModel.phoneNumber.isEmpty()) {
                    Toast.makeText(context, "휴대폰 번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                } else if (viewModel.phoneNumber.length == 11) {
                    onSendMessageClick(viewModel.phoneNumber)
                    Toast.makeText(context, "인증번호를 전송하였습니다.", Toast.LENGTH_SHORT).show()
                    viewModel.updateIsSendNumber(true)
                } else {
                    Toast.makeText(context, "올바른 휴대폰 번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            },
            padding = 5
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextFieldWithButton(
            text = viewModel.certificationNumber,
            width = 62,
            height = 27,
            textFieldLabel = "인증번호",
            keyboardType = KeyboardType.Number,
            placeholder = "인증번호 6자리",
            buttonLabel = "인증 확인",
            onTextChanged = { if (it.length <= 6) viewModel.updateCertificationNumber(it) },
            onClick = {
                if (!isSendNumber) {
                    Toast.makeText(context, "먼저 인증번호를 전송해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    if (viewModel.certificationNumber.isEmpty()) {
                        Toast.makeText(context, "인증 번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                    } else if (viewModel.certificationNumber.length == 6) {
                        onVerifyCodeClick(it) { viewModel.updateIsCertified(it) }
                    } else {
                        Toast.makeText(context, "인증 번호는 6자리로 입력해주세요", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            padding = 5
        )
        Spacer(modifier = Modifier.weight(1f))
        ConnectDogBottomButton(
            content = "다음",
            enabled = isCertified,
            onClick = { viewModel.test(userType = userType, context = context) },
            modifier =
            Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
        Spacer(modifier = Modifier.height((imeHeight + 32).dp))
    }
}
