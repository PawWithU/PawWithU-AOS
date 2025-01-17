package com.kusitms.connectdog.feature.mypage.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.component.ConnectDogOutlinedButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.mypage.R
import com.kusitms.connectdog.feature.mypage.viewmodel.MyAccountViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ManageAccountScreen(
    userType: UserType,
    onBackClick: () -> Unit,
    onNavigateToPasswordChange: (UserType) -> Unit,
    viewModel: MyAccountViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchAccountInfo(userType)
    }

    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()
    val phone by viewModel.phoneNumber.collectAsState()
    val socialType by viewModel.socialType.collectAsState()

    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.manage_account,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = null,
                onNavigationClick = onBackClick
            )
        }
    ) {
        Content(
            userType = userType,
            name = name,
            phone = phone,
            email = email,
            socialType = socialType,
            onNavigateToPasswordChange = onNavigateToPasswordChange
        )
    }
}

@Composable
private fun Content(
    userType: UserType,
    name: String,
    phone: String,
    email: String,
    socialType: String?,
    onNavigateToPasswordChange: (UserType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 68.dp)
    ) {
        Text(
            text = "회원 정보",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(30.dp))
        Information(title = "이름", content = name)
        Spacer(modifier = Modifier.height(20.dp))
        Information(title = "휴대폰 번호", content = phone)
        Spacer(modifier = Modifier.height(20.dp))
        if (socialType == null) {
            Information(title = "이메일", content = email)
            Spacer(modifier = Modifier.height(20.dp))
        }

        when (userType) {
            UserType.INTERMEDIATOR -> {
                ChangePassword {
                    onNavigateToPasswordChange(UserType.INTERMEDIATOR)
                }
            }

            else -> {
                if (socialType == null) {
                    ChangePassword {
                        onNavigateToPasswordChange(UserType.NORMAL_VOLUNTEER)
                    }
                } else {
                    Text(
                        text = "SNS 연동",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.width(80.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Image(
                        painter = painterResource(
                            id = when (socialType) {
                                "KAKAO" -> R.drawable.ic_kakao_linking
                                "NAVER" -> R.drawable.ic_naver_linkking
                                else -> R.drawable.ic_naver_linkking
                            }
                        ),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
private fun ChangePassword(onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "비밀번호",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.width(80.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        ConnectDogOutlinedButton(
            width = 41,
            height = 26,
            text = "변경",
            padding = 3,
            onClick = onClick
        )
    }
}

@Composable
private fun Information(
    title: String,
    content: String
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.width(80.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.bodyLarge,
            color = Gray2
        )
    }
}

@Preview
@Composable
private fun test() {
    ConnectDogTheme {
//        ManageAccountScreen()
    }
}
