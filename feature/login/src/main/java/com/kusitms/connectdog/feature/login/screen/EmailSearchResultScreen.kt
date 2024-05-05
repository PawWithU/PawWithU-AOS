package com.kusitms.connectdog.feature.login.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogOutlinedButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.feature.login.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun EmailSearchResultScreen(
    onBackClick: () -> Unit
//    navigateToLogin: () -> Unit
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
//            imeHeight = imeHeight,
//            viewModel = viewModel
        )
    }
}

@Composable
private fun Content() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = "입력하신 번호로 찾은\n계정 정보입니다.",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "정보와 일치하는 계정이 존재합니다.\n아래 계정으로 로그인해주세요.",
            color = Gray2,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "kang9936@naver.com")
            Spacer(modifier = Modifier.weight(1f))
            ConnectDogOutlinedButton(
                width = 50,
                height = 28,
                text = "로그인",
                padding = 5,
                modifier = Modifier.padding(top = 6.dp, end = 16.dp),
                onClick = {
                }
            )
        }
    }
}
