package com.kusitms.connectdog.feature.login.screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kusitms.connectdog.core.designsystem.component.ConnectDogIconBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.SpeechBubble
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.KAKAO
import com.kusitms.connectdog.core.designsystem.theme.NAVER
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.core.util.SocialType
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.login.R
import com.kusitms.connectdog.feature.login.viewmodel.LoginViewModel
import com.kusitms.connectdog.feature.login.viewmodel.Provider
import kotlinx.coroutines.launch

@Composable
internal fun LoginRoute(
    finish: () -> Unit,
    onNavigateToNormalLogin: (UserType) -> Unit,
    onNavigateToSignup: (UserType) -> Unit,
    onNavigateToVolunteerHome: () -> Unit,
    onNavigateToIntermediatorHome: () -> Unit,
    onNavigateToEmailSearch: (UserType) -> Unit,
    onNavigateToPasswordSearch: (UserType) -> Unit
) {
    BackHandler { finish() }
    LoginScreen(
        onNavigateToNormalLogin = onNavigateToNormalLogin,
        onNavigateToSignup = onNavigateToSignup,
        onNavigateToVolunteerHome = onNavigateToVolunteerHome,
        onNavigateToIntermediatorHome = onNavigateToIntermediatorHome,
        onNavigateToEmailSearch = onNavigateToEmailSearch,
        onNavigateToPasswordSearch = onNavigateToPasswordSearch
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    onNavigateToNormalLogin: (UserType) -> Unit,
    onNavigateToSignup: (UserType) -> Unit,
    onNavigateToVolunteerHome: () -> Unit,
    onNavigateToIntermediatorHome: () -> Unit,
    onNavigateToEmailSearch: (UserType) -> Unit,
    onNavigateToPasswordSearch: (UserType) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                onClick = { focusManager.clearFocus() },
                indication = null,
                interactionSource = interactionSource
            )
    ) {
        Text(
            modifier = Modifier.padding(start = 20.dp, top = 32.dp, bottom = 32.dp),
            text = stringResource(id = R.string.introduce),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        LoginContent(
            onNavigateToNormalLogin,
            onNavigateToSignup,
            onNavigateToVolunteerHome,
            onNavigateToIntermediatorHome,
            onNavigateToEmailSearch,
            onNavigateToPasswordSearch
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = com.kusitms.connectdog.core.designsystem.R.drawable.ic_main),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun LoginContent(
    onNavigateToNormalLogin: (UserType) -> Unit,
    onNavigateToSignup: (UserType) -> Unit,
    onNavigateToVolunteerHome: () -> Unit,
    onNavigateToIntermediatorHome: () -> Unit,
    onNavigateToEmailSearch: (UserType) -> Unit,
    onNavigateToPasswordSearch: (UserType) -> Unit
) {
    val pages = listOf("이동봉사자 회원", "이동봉사 모집자 회원")
    Column(
        modifier = Modifier.height(340.dp)
    ) {
        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()

        Row(
            modifier = Modifier
                .height(37.dp)
                .fillMaxWidth(0.5f)
                .padding(
                    start = if(pagerState.currentPage == 0) 0.dp else 10.dp,
                    end = if(pagerState.currentPage == 0) 10.dp else 0.dp
                )
                .align(if(pagerState.currentPage == 0) Alignment.End else Alignment.Start)
        ) {
            SpeechBubble(
                text = "이동봉사 공고 신청자를 모집한다면?",
                fontSize = 10,
                fontColor = PetOrange,
                fontWeight = FontWeight.SemiBold
            )
        }

        TabRow(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp),
            selectedTabIndex = pagerState.currentPage
        ) {
            pages.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            text = title,
                            color = if (pagerState.currentPage == index) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                Gray2
                            }
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.scrollToPage(index)
                        }
                    }
                )
            }
        }
        HorizontalPager(
            count = pages.size,
            state = pagerState
        ) {
            when (it) {
                0 -> Volunteer(
                    onNavigateToNormalLogin = onNavigateToNormalLogin,
                    onNavigateToSignup = onNavigateToSignup,
                    onNavigateToVolunteerHome = onNavigateToVolunteerHome
                )

                1 -> Intermediator(
                    onNavigateToIntermediatorHome = onNavigateToIntermediatorHome,
                    onNavigateToSignup = onNavigateToSignup,
                    onNavigateToEmailSearch = onNavigateToEmailSearch,
                    onNavigateToPasswordSearch = onNavigateToPasswordSearch
                )
            }
        }
    }
}

@Composable
private fun Volunteer(
    onNavigateToNormalLogin: (UserType) -> Unit,
    onNavigateToSignup: (UserType) -> Unit,
    onNavigateToVolunteerHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val socialType by viewModel.socialType.collectAsState()

    socialType?.let {
        when (it) {
            SocialType.VOLUNTEER -> onNavigateToVolunteerHome()
            SocialType.GUEST -> onNavigateToSignup(UserType.SOCIAL_VOLUNTEER)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 32.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        ConnectDogIconBottomButton(
            iconId = R.drawable.ic_kakao,
            contentDescription = "kakao login",
            onClick = { viewModel.initSocialLogin(Provider.KAKAO, context) },
            content = stringResource(id = R.string.kakao_login),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            color = KAKAO,
            textColor = Color(0xFF373737)
        )
        Spacer(modifier = Modifier.height(10.dp))
        ConnectDogIconBottomButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            color = NAVER,
            iconId = R.drawable.ic_naver,
            contentDescription = "naver login",
            onClick = { viewModel.initSocialLogin(Provider.NAVER, context) },
            content = stringResource(id = R.string.naver_login)
        )
        Spacer(modifier = Modifier.height(30.dp))
        SignUpOrLogin(onNavigateToSignup, onNavigateToNormalLogin, UserType.NORMAL_VOLUNTEER)
    }
}

@Composable
private fun Intermediator(
    onNavigateToIntermediatorHome: () -> Unit,
    onNavigateToSignup: (UserType) -> Unit,
    onNavigateToEmailSearch: (UserType) -> Unit,
    onNavigateToPasswordSearch: (UserType) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val isLoginSuccessful by viewModel.isLoginSuccessful.collectAsState()

    LaunchedEffect(key1 = viewModel) {
        viewModel.isLoginSuccessful.collect {
            if (it == true) { onNavigateToIntermediatorHome() }
        }
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 32.dp, start = 20.dp, end = 20.dp)
            .fillMaxHeight()
    ) {
        ConnectDogTextField(
            text = viewModel.email,
            label = "이메일",
            placeholder = "이메일 입력",
            keyboardType = KeyboardType.Text,
            onTextChanged = { viewModel.updateEmail(it) },
            isError = (isLoginSuccessful == false)
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextField(
            text = viewModel.password,
            label = "비밀번호",
            placeholder = "비밀번호 입력",
            keyboardType = KeyboardType.Password,
            onTextChanged = { viewModel.updatePassword(it) },
            isError = (isLoginSuccessful == false)
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogNormalButton(
            modifier = Modifier
                .fillMaxWidth(),
            content = stringResource(id = R.string.login),
            onClick = {
                viewModel.initIntermediatorLogin()
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        AccountFind(
            onNavigateToSignup = onNavigateToSignup,
            onNavigateToEmailSearch = { onNavigateToEmailSearch(UserType.INTERMEDIATOR) },
            onNavigateToPasswordSearch = { onNavigateToPasswordSearch(UserType.INTERMEDIATOR) },
            userType = UserType.INTERMEDIATOR
        )
    }
}

@Composable
private fun SignUpOrLogin(
    onNavigateToSignup: (UserType) -> Unit,
    onNavigateToNormalLogin: (UserType) -> Unit,
    userType: UserType
) {
    Row {
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
            modifier = Modifier.clickable { onNavigateToNormalLogin(userType) },
            text = "이메일 로그인",
            fontSize = 12.sp,
            color = Gray2
        )
    }
}
