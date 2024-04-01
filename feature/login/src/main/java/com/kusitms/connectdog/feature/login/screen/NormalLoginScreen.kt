package com.kusitms.connectdog.feature.login.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.component.ConnectDogErrorCard
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.login.viewmodel.LoginViewModel

private const val TAG = "EmailLoginScreen"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun NormalLoginScreen(
    userType: UserType,
    onBackClick: () -> Unit,
    onNavigateToSignUp: (UserType) -> Unit,
    onNavigateToVolunteerHome: () -> Unit,
    onNavigateToIntermediatorHome: () -> Unit,
    onNavigateToEmailSearch: () -> Unit,
    onNavigateToPasswordSearch: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isLoginSuccessful by viewModel.isLoginSuccessful.collectAsState()

    isLoginSuccessful?.let {
        if (it) {
            when (userType) {
                UserType.INTERMEDIATOR -> onNavigateToIntermediatorHome()
                else -> onNavigateToVolunteerHome()
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .clickable(
                onClick = { focusManager.clearFocus() },
                indication = null,
                interactionSource = interactionSource
            ),
        topBar = {
            ConnectDogTopAppBar(
                titleRes = when (userType) {
                    UserType.SOCIAL_VOLUNTEER -> R.string.volunteer_login
                    UserType.NORMAL_VOLUNTEER -> R.string.volunteer_login
                    UserType.INTERMEDIATOR -> R.string.intermediator_login
                },
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick
            )
        }
    ) {
        Content(
            viewModel = viewModel,
            onNavigateToSignUp = onNavigateToSignUp,
            onNavigateToEmailSearch = onNavigateToEmailSearch,
            onNavigateToPasswordSearch = onNavigateToPasswordSearch,
            userType = userType,
            isLoginSuccessful = isLoginSuccessful
        )
    }
}

@Composable
private fun Content(
    viewModel: LoginViewModel,
    onNavigateToSignUp: (UserType) -> Unit,
    onNavigateToEmailSearch: () -> Unit,
    onNavigateToPasswordSearch: () -> Unit,
    userType: UserType,
    isLoginSuccessful: Boolean?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 98.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            ConnectDogTextField(
                text = viewModel.email,
                label = "이메일",
                placeholder = "이메일 입력",
                keyboardType = KeyboardType.Text,
                onTextChanged = { viewModel.updateEmail(it) },
                isError = isLoginSuccessful?.let { !it } ?: run { false }
            )
            Spacer(modifier = Modifier.height(12.dp))
            ConnectDogTextField(
                text = viewModel.password,
                label = "비밀번호",
                placeholder = "비밀번호 입력",
                keyboardType = KeyboardType.Password,
                onTextChanged = { viewModel.updatePassword(it) },
                isError = isLoginSuccessful?.let { !it } ?: run { false }
            )
            Spacer(modifier = Modifier.height(12.dp))
            ConnectDogNormalButton(
                content = "로그인",
                color = MaterialTheme.colorScheme.primary,
                onClick = {
                    when (userType) {
                        UserType.INTERMEDIATOR -> {
                            viewModel.initIntermediatorLogin()
                        }

                        UserType.NORMAL_VOLUNTEER -> {
                            viewModel.initVolunteerLogin()
                        }

                        UserType.SOCIAL_VOLUNTEER -> {}
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            NormalLogin(
                onNavigateToSignup = onNavigateToSignUp,
                onNavigateToEmailSearch = onNavigateToEmailSearch,
                onNavigateToPasswordSearch = onNavigateToPasswordSearch,
                userType = userType
            )
            Spacer(modifier = Modifier.height(40.dp))
            if (isLoginSuccessful?.let { !it } ?: run { false }) {
                ConnectDogErrorCard(R.string.login_error)
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.ic_main_large),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
    }
}

@Composable
private fun NormalLogin(
    onNavigateToSignup: (UserType) -> Unit,
    onNavigateToEmailSearch: () -> Unit,
    onNavigateToPasswordSearch: () -> Unit,
    userType: UserType
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.clickable { onNavigateToSignup(userType) },
            text = "이메일로 회원가입",
            fontSize = 12.sp,
            color = Gray2
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "|",
            fontSize = 12.sp,
            color = Gray2
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            modifier = Modifier.clickable { onNavigateToEmailSearch() },
            text = "이메일 찾기",
            fontSize = 12.sp,
            color = Gray2
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "|",
            fontSize = 12.sp,
            color = Gray2
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            modifier = Modifier.clickable { onNavigateToPasswordSearch() },
            text = "비밀번호 찾기",
            fontSize = 12.sp,
            color = Gray2
        )
    }
}
