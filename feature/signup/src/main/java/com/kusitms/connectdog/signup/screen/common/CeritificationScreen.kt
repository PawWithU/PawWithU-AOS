package com.kusitms.connectdog.signup.screen.common

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextFieldWithTimer
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray6
import com.kusitms.connectdog.core.designsystem.theme.Gray8
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.signup.R
import com.kusitms.connectdog.signup.state.CertificationSideEffect
import com.kusitms.connectdog.signup.viewmodel.CertificationViewModel
import com.kusitms.connectdog.signup.viewmodel.SignUpViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CertificationScreen(
    onBackClick: () -> Unit,
    onNavigateToRegisterEmail: (UserType) -> Unit,
    onNavigateToVolunteerProfile: (UserType) -> Unit,
    onSendMessageClick: (String) -> Unit,
    onVerifyCodeClick: (String, (Boolean) -> Unit) -> Unit,
    imeHeight: Int,
    userType: UserType,
    signUpViewModel: SignUpViewModel,
    viewModel: CertificationViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = when (userType) {
                    UserType.INTERMEDIATOR -> R.string.intermediator_signup
                    else -> R.string.volunteer_signup
                },
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick
            )
        }
    ) {
        Content(
            onNavigateToRegisterEmail = { onNavigateToRegisterEmail(userType) },
            onNavigateToVolunteerProfile = { onNavigateToVolunteerProfile(userType) },
            onSendMessageClick = onSendMessageClick,
            onVerifyCodeClick = onVerifyCodeClick,
            imeHeight = imeHeight,
            userType = userType,
            viewModel = viewModel,
            signUpViewModel = signUpViewModel
        )
    }
}

@Composable
private fun Content(
    imeHeight: Int,
    userType: UserType,
    onNavigateToRegisterEmail: (UserType) -> Unit,
    onNavigateToVolunteerProfile: (UserType) -> Unit,
    onSendMessageClick: (String) -> Unit,
    onVerifyCodeClick: (String, (Boolean) -> Unit) -> Unit,
    signUpViewModel: SignUpViewModel,
    viewModel: CertificationViewModel
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val uiState by viewModel.collectAsState()

    LaunchedEffect(key1 = signUpViewModel) {
        signUpViewModel.isDuplicatePhoneNumber.collect {
            if (it) {
                Toast.makeText(context, "중복된 휴대폰 번호입니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "인증번호를 전송하였습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is CertificationSideEffect.NavigateToProfile -> {
                signUpViewModel.updateName(uiState.name)
                signUpViewModel.updatePhoneNumber(uiState.phoneNumber)
                when (userType) {
                    UserType.SOCIAL_VOLUNTEER -> onNavigateToVolunteerProfile(userType)
                    else -> onNavigateToRegisterEmail(userType)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 32.dp)
            .clickable(
                onClick = { focusManager.clearFocus() },
                indication = null,
                interactionSource = interactionSource
            )
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = stringResource(id = R.string.certification_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(40.dp))
        ConnectDogTextField(
            text = uiState.name,
            label = "이름",
            placeholder = "이름 입력",
            keyboardType = KeyboardType.Text,
            onTextChanged = viewModel::onNameChanged
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextField(
            text = uiState.phoneNumber,
            onTextChanged = viewModel::onPhoneNumberChanged,
            label = stringResource(id = R.string.phone_number),
            placeholder = "'-'빼고 입력",
            keyboardType = KeyboardType.Number
        )

        if (uiState.isSendCertificationNumber) {
            Spacer(modifier = Modifier.height(12.dp))
            ConnectDogTextFieldWithTimer(
                text = uiState.certificationNumber,
                textFieldLabel = "인증번호",
                placeholder = "숫자 6자리",
                keyboardType = KeyboardType.Number,
                onTextChanged = viewModel::onChangeCertificationNumber
            )
            Spacer(modifier = Modifier.height(28.dp))
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "인증번호가 오지 않는다면?",
                    color = Gray6,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier.clickable { },
                    text = "재발송",
                    fontSize = 12.sp,
                    color = Gray8,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        ConnectDogNormalButton(
            content = uiState.bottomButtonText,
            enabled = if (!uiState.isSendCertificationNumber) uiState.enableNext else uiState.enableCertification,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = { viewModel.onNextClick(onSendMessageClick, onVerifyCodeClick) }
        )
        Spacer(modifier = Modifier.height((imeHeight + 32).dp))
    }
}
