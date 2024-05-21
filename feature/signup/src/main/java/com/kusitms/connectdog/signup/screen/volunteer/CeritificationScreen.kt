package com.kusitms.connectdog.signup.screen.volunteer

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextFieldWithButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Orange40
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.signup.viewmodel.CertificationViewModel
import com.kusitms.connectdog.signup.viewmodel.SignUpViewModel

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
    val isSendNumber by remember { viewModel.isSendNumber }.collectAsState()
    val isCertified by remember { viewModel.isCertified }.collectAsState()
//    val isDuplicatePhoneNumber = signUpViewModel.isDuplicatePhoneNumber.collectAsState()

    LaunchedEffect(key1 = signUpViewModel) {
        signUpViewModel.isDuplicatePhoneNumber.collect {
            if (it) {
                Toast.makeText(context, "중복된 휴대폰 번호입니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "인증번호를 전송하였습니다.", Toast.LENGTH_SHORT).show()
                onSendMessageClick(viewModel.phoneNumber)
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 32.dp)
            .clickable(
                onClick = { focusManager.clearFocus() },
                indication = null,
                interactionSource = interactionSource
            )
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = stringResource(id = com.kusitms.connectdog.feature.signup.R.string.certification_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(40.dp))
        ConnectDogTextField(
            text = viewModel.name,
            label = "이름",
            placeholder = "이름 입력",
            keyboardType = KeyboardType.Text,
            onTextChanged = { viewModel.updateName(it) }
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextFieldWithButton(
            text = viewModel.phoneNumber,
            width = 62,
            height = 27,
            textFieldLabel = "휴대폰 번호",
            placeholder = "'-'빼고 입력",
            buttonLabel = "인증 요청",
            keyboardType = KeyboardType.Number,
            padding = 5,
            onTextChanged = { viewModel.updatePhoneNumber(it) },
            onClick = {
                if (viewModel.phoneNumber.isEmpty()) {
                    Toast.makeText(context, "휴대폰 번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    signUpViewModel.checkIsDuplicatePhoneNumber(userType, viewModel.phoneNumber)
                    viewModel.updateIsSendNumber(true)
                }
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextFieldWithButton(
            text = viewModel.certificationNumber,
            width = 62,
            height = 27,
            textFieldLabel = "인증번호",
            placeholder = "숫자 6자리",
            buttonLabel = "인증 확인",
            keyboardType = KeyboardType.Number,
            padding = 5,
            onTextChanged = { viewModel.updateCertificationNumber(it) },
            onClick = {
                Log.d("send", isSendNumber.toString())
                viewModel.updateIsCertified(true)
//                if (!isSendNumber) {
//                    Toast.makeText(context, "먼저 인증번호를 전송해주세요", Toast.LENGTH_SHORT).show()
//                } else {
//                    if (viewModel.certificationNumber.isEmpty()) {
//                        Toast.makeText(context, "인증 번호를 입력해주세요", Toast.LENGTH_SHORT).show()
//                    } else {
//                        onVerifyCodeClick(it) {
//                            viewModel.updateIsCertified(it)
//                            Log.d("casz", isCertified.toString())
//                        }
//                    }
//                }
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        ConnectDogNormalButton(
            content = "다음",
            color = if (viewModel.name.isNotEmpty() && isCertified) {
                PetOrange
            } else {
                Orange40
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = {
                if (viewModel.name.isNotEmpty() && isCertified) {
                    viewModel.postAdditionalAuth()
                    signUpViewModel.updatePhoneNumber(viewModel.phoneNumber)
                    signUpViewModel.updateName(viewModel.name)
                    when (userType) {
                        UserType.SOCIAL_VOLUNTEER -> onNavigateToVolunteerProfile(userType)
                        else -> onNavigateToRegisterEmail(userType)
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}
