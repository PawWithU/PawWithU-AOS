package com.kusitms.connectdog.signup.screen.common

import android.view.Gravity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.core.designsystem.theme.Orange_40
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.signup.viewmodel.TermsViewModel

@Composable
internal fun SignUpRoute(
    onBackClick: () -> Unit,
    navigateToCertification: (UserType) -> Unit,
    openWebBrowser: (String) -> Unit,
    userType: UserType
) {
    TermScreen(
        onBackClick = onBackClick,
        userType = userType,
        navigateToCertification = navigateToCertification,
        openWebBrowser = openWebBrowser
    )
}

@Composable
private fun TermScreen(
    userType: UserType,
    onBackClick: () -> Unit,
    navigateToCertification: (UserType) -> Unit,
    openWebBrowser: (String) -> Unit,
    viewModel: TermsViewModel = hiltViewModel()
) {
    val allChecked by viewModel.allChecked.observeAsState(initial = false)
    val privacyChecked by viewModel.privacyChecked.observeAsState(initial = false)
    val advertisementChecked by viewModel.advertisementChecked.observeAsState(initial = false)
    val termsChecked by viewModel.termsChecked.observeAsState(initial = false)
    val isAvailableNext by viewModel.isAvailableNext.observeAsState(initial = false)

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    BackHandler { onBackClick() }

    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(bottom = 32.dp)
            .clickable(
                onClick = { focusManager.clearFocus() },
                indication = null,
                interactionSource = interactionSource
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
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
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "코넥독 서비스 이용약관에\n동의해주세요",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 20.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))

            CustomCheckbox(
                text = "모두 동의",
                checked = allChecked,
                hasDetail = false,
                onCheckedChange = {
                    viewModel.updateAllChecked()
                    viewModel.updateTermsChecked()
                    viewModel.updatePrivacyChecked()
                    viewModel.updateAdvertisementChecked()
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalLine()
            Spacer(modifier = Modifier.height(16.dp))
            CustomCheckbox(
                text = "[필수] 이용약관 동의",
                checked = termsChecked,
                hasDetail = true,
                onDetailClick = { openWebBrowser("https://docs.google.com/document/d/1Rr2sqZVGLVIqNyqI_YoaBazTZ2tqapRlX2zdqtM1ICs/edit?usp=sharing") },
                onCheckedChange = { viewModel.updateTermsChecked() }
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomCheckbox(
                text = "[필수] 개인정보 수집 및 이용 동의",
                checked = privacyChecked,
                onCheckedChange = { viewModel.updatePrivacyChecked() },
                hasDetail = true,
                onDetailClick = { openWebBrowser("https://docs.google.com/document/d/14nF3hzCFfTVGSecKDddO4FJYy27v_c8BUkAU2w_m1O4/edit?usp=sharing") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomCheckbox(
                text = "[선택] 광고성 정보 수신 동의",
                checked = advertisementChecked,
                onCheckedChange = { viewModel.updateAdvertisementChecked() },
                hasDetail = true,
                onDetailClick = { openWebBrowser("https://docs.google.com/document/d/14nF3hzCFfTVGSecKDddO4FJYy27v_c8BUkAU2w_m1O4/edit?usp=sharing") }
            )
        }
        ConnectDogNormalButton(
            content = "다음",
            color = if (isAvailableNext) {
                PetOrange
            } else {
                Orange_40
            },
            modifier =
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.BottomCenter)
                .padding(horizontal = 20.dp),
            onClick = {
                if (isAvailableNext) {
                    navigateToCertification(userType)
                } else {
                    val toast = Toast.makeText(context, "모든 약관에 동의해주세요", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.BOTTOM, 10, 1000)
                    toast.show()
                }
            }
        )
    }
}

@Composable
fun HorizontalLine() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(horizontal = 20.dp)
    ) {
        drawLine(
            color = Gray3,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = 1f
        )
    }
}

@Composable
fun CustomCheckbox(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    hasDetail: Boolean,
    onDetailClick: () -> Unit = {}
) {
    var isChecked by remember { mutableStateOf(checked) }

    if (checked != isChecked) {
        isChecked = checked
    }

    Row(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.clickable {
                isChecked = !isChecked
                onCheckedChange(isChecked)
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(
                    id = R.drawable.ic_checked
                ),
                contentDescription = "Custom Checkbox",
                tint = if (isChecked) MaterialTheme.colorScheme.primary else Gray4,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (isChecked) Color.Black else Gray2
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        if (hasDetail) {
            Text(
                text = "보기",
                style = TextStyle(textDecoration = TextDecoration.Underline),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (isChecked) Gray1 else Gray2,
                modifier = Modifier.clickable { onDetailClick() }
            )
        }
    }
}
