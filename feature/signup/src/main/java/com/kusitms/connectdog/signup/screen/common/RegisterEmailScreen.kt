package com.kusitms.connectdog.signup.screen.common

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextFieldWithButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Orange_40
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.signup.viewmodel.RegisterEmailViewModel
import com.kusitms.connectdog.signup.viewmodel.SignUpViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterEmailScreen(
    onBackClick: () -> Unit,
    userType: UserType,
    onNavigateToRegisterPassword: (UserType) -> Unit,
    signUpViewModel: SignUpViewModel,
    viewModel: RegisterEmailViewModel = hiltViewModel(),
    imeHeight: Int
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current
    val isValidEmail by viewModel.isValidEmail.collectAsState()
    val isEmailVerified by viewModel.isEmailVerified.collectAsState()
    val isEmailDuplicated by viewModel.isEmailDuplicated.collectAsState()

    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = when (userType) {
                    UserType.SOCIAL_VOLUNTEER -> R.string.volunteer_signup
                    UserType.NORMAL_VOLUNTEER -> R.string.volunteer_signup
                    UserType.INTERMEDIATOR -> R.string.intermediator_signup
                },
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .clickable(
                    onClick = { focusManager.clearFocus() },
                    indication = null,
                    interactionSource = interactionSource
                )
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Text(
                text = "로그인에 사용할\n이메일을 입력해주세요",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(40.dp))
            ConnectDogTextFieldWithButton(
                text = viewModel.email,
                width = 62,
                height = 27,
                textFieldLabel = "이메일",
                placeholder = "이메일 입력",
                buttonLabel = "인증 요청",
                isError = (isValidEmail ?: false) || (isEmailDuplicated ?: false),
                onTextChanged = {
                    viewModel.updateEmail(it)
                    viewModel.updateEmailValidity()
                },
                onClick = {
                    if (isValidEmail == true) {
                        Toast.makeText(context, "유효한 이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
                    } else if (isEmailDuplicated == true) {
                        Toast.makeText(context, "이미 가입된 이메일입니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.postEmail()
                        Toast.makeText(context, "이메일을 전송했습니다", Toast.LENGTH_SHORT).show()
                    }
                },
                padding = 5
            )
            Spacer(modifier = Modifier.height(12.dp))
            ConnectDogTextFieldWithButton(
                text = viewModel.certificationNumber,
                width = 62,
                height = 27,
                textFieldLabel = "인증 번호",
                placeholder = "숫자 6자리",
                buttonLabel = "인증 확인",
                keyboardType = KeyboardType.Text,
                onTextChanged = { viewModel.updateCertificationNumber(it) },
                onClick = {
                    if (it.isNotEmpty()) {
                        viewModel.checkCertificationNumber()
                    } else {
                        Toast.makeText(context, "인증번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                    }
                },
                padding = 5,
                isError = isEmailVerified == false
            )
            Spacer(modifier = Modifier.weight(1f))
            ConnectDogNormalButton(
                content = "다음",
                color = if (isEmailVerified == true) { PetOrange } else { Orange_40 },
                onClick = {
                    if (isEmailVerified == true) {
                        signUpViewModel.updateEmail(viewModel.email)
                        onNavigateToRegisterPassword(userType)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
            Spacer(modifier = Modifier.height((imeHeight + 32).dp))
        }
    }
}

@Preview
@Composable
private fun test() {
    ConnectDogTheme {
        RegisterEmailScreen(
            onBackClick = {},
            userType = UserType.NORMAL_VOLUNTEER,
            onNavigateToRegisterPassword = {},
            hiltViewModel(),
            imeHeight = 10
        )
    }
}