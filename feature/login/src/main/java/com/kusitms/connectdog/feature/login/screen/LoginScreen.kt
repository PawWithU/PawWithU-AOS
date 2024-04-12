package com.kusitms.connectdog.feature.login.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
    onNavigateToNormalLogin: (UserType) -> Unit,
    onNavigateToSignup: (UserType) -> Unit,
    onNavigateToVolunteerHome: () -> Unit
) {
    LoginScreen(
        onNavigateToNormalLogin = onNavigateToNormalLogin,
        onNavigateToSignup = onNavigateToSignup,
        onNavigateToVolunteerHome = onNavigateToVolunteerHome
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    onNavigateToNormalLogin: (UserType) -> Unit,
    onNavigateToSignup: (UserType) -> Unit,
    onNavigateToVolunteerHome: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
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
            onNavigateToVolunteerHome
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
    onNavigateToVolunteerHome: () -> Unit
) {
    val pages = listOf("이동봉사자 회원", "이동봉사 모집자 회원")
    Column(
        modifier = Modifier.height(340.dp)
    ) {
        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()
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
                    onNavigateToNormalLogin = onNavigateToNormalLogin,
                    onNavigateToSignup = onNavigateToSignup
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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 32.dp)
    ) {
        BubbleInfo()
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

    socialType?.let {
        when (it) {
            SocialType.VOLUNTEER -> onNavigateToVolunteerHome()
            SocialType.GUEST -> {}
        }
    }
}

@Composable
private fun Intermediator(
    onNavigateToNormalLogin: (UserType) -> Unit,
    onNavigateToSignup: (UserType) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val isLoginSuccessful by viewModel.isLoginSuccessful.collectAsState()

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
            modifier = Modifier
                .fillMaxWidth(),
            content = stringResource(id = R.string.login),
            onClick = { viewModel.initIntermediatorLogin() }
        )
        Spacer(modifier = Modifier.height(30.dp))
        AccountFind(
            onNavigateToSignup = onNavigateToSignup,
            onNavigateToEmailSearch = { },
            onNavigateToPasswordSearch = { },
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

@Composable
private fun BubbleInfo() {
    Box {
        BubbleShape()
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "이동봉사 공고를 찾고 계신다면, 이동봉사자 회원으로!",
            fontSize = 12.sp,
            color = PetOrange,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun BubbleShape() {
    val density = LocalDensity.current
    val tailWidth = with(density) { 10.dp.toPx() }
    val tailHeight = with(density) { 9.dp.toPx() }
    val strokeWidth = with(density) { 1.dp.toPx() }

    Canvas(
        modifier = Modifier
            .height(28.dp)
            .fillMaxWidth()
            .padding(horizontal = 37.dp)
    ) {
        val width = size.width
        val height = size.height

        val path = Path().apply {
            val cornerRadius = 100.dp.toPx()
            addRoundRect(RoundRect(0f, 0f, width, height, cornerRadius, cornerRadius))
        }

        val path2 = Path().apply {
            val tailStartX = (width / 2) - (tailWidth / 2)
            val tailStartY = height
            moveTo(tailStartX + 2f, tailStartY)
            lineTo(tailStartX + tailWidth - 2f, tailStartY)
        }

        val path3 = Path().apply {
            val tailStartX = (width / 2) - (tailWidth / 2)
            val tailStartY = height

            moveTo(tailStartX, tailStartY)
            lineTo(tailStartX + tailWidth / 2, tailStartY + tailHeight) // 꼬리의 끝점
            lineTo(tailStartX + tailWidth, tailStartY)
        }

        drawPath(
            path = path,
            color = PetOrange,
            style = Stroke(width = strokeWidth)
        )

        drawPath(
            path = path2,
            color = Color.White,
            style = Stroke(width = strokeWidth + 2.dp.toPx())
        )

        drawPath(
            path = path3,
            color = PetOrange,
            style = Stroke(width = strokeWidth)
        )
    }
}

@Preview
@Composable
private fun test() {
    ConnectDogTheme {
        BubbleInfo()
    }
}
