package com.kusitms.connectdog.feature.login.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextFieldWithButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray5
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.core.designsystem.theme.Red1
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.login.R
import com.kusitms.connectdog.feature.login.viewmodel.PasswordSearchAuthViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PasswordSearchAuthScreen(
    onBackClick: () -> Unit,
    onNavigateToPasswordSearch: (UserType) -> Unit,
    imeHeight: Int,
    userType: UserType
) {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.password_search,
                navigationType = TopAppBarNavigationType.BACK,
                onNavigationClick = onBackClick
            )
        }
    ) {
        Content(
            imeHeight = imeHeight,
            onNavigateToPasswordSearch = onNavigateToPasswordSearch,
            userType = userType
        )
    }
}

@Composable
private fun Content(
    imeHeight: Int,
    onNavigateToPasswordSearch: (UserType) -> Unit,
    userType: UserType,
    viewModel: PasswordSearchAuthViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current
    val isValidEmail by viewModel.isValidEmail.collectAsState()
    val isEmailVerified by viewModel.isEmailVerified.collectAsState()
    val isEmailError by viewModel.isEmailError.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp, bottom = 24.dp)
            .padding(horizontal = 20.dp)
            .clickable(
                onClick = { focusManager.clearFocus() },
                indication = null,
                interactionSource = interactionSource
            )
    ) {
        Text(
            text = "이메일 인증을\n진행해주세요",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 28.sp
        )
        Spacer(modifier = Modifier.height(40.dp))
        ConnectDogTextFieldWithButton(
            text = viewModel.email,
            width = 62,
            height = 27,
            textFieldLabel = "이메일",
            placeholder = "이메일 입력",
            buttonLabel = "인증 요청",
            isError = (isValidEmail ?: false) || (isEmailError == true),
            onTextChanged = {
                viewModel.updateEmail(it)
                viewModel.updateEmailValidity()
            },
            borderColor = if (isValidEmail == false) PetOrange else Gray5,
            onClick = { viewModel.postEmail(context, userType) },
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
            borderColor = if (viewModel.isEmailVerified.value == true) PetOrange else if (viewModel.isEmailVerified.value == false) Red1 else Gray5,
            onClick = { viewModel.checkCertificationNumber(context) },
            padding = 5,
            isError = isEmailVerified == false
        )
        Spacer(modifier = Modifier.weight(1f))
        ConnectDogBottomButton(
            onClick = { onNavigateToPasswordSearch(userType) },
            content = "인증 완료",
            enabled = isEmailVerified == true
        )
        Spacer(modifier = Modifier.height(imeHeight.dp))
    }
}
