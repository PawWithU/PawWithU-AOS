package com.kusitms.connectdog.feature.home.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kusitms.connectdog.core.designsystem.component.ConnectDogReview
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.NetworkImage
import com.kusitms.connectdog.core.designsystem.component.ReviewType
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.core.model.Review
import com.kusitms.connectdog.feature.home.HomeViewModel
import com.kusitms.connectdog.feature.home.R
import com.kusitms.connectdog.feature.home.state.ReviewUiState

@Composable
fun ReviewScreen(
    onBackClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val reviewUiState by viewModel.reviewUiState.collectAsStateWithLifecycle()

    Column {
        TopAppBar {
            onBackClick()
        }
        ReviewContent(uiState = reviewUiState)
    }
}

@Composable
private fun TopAppBar(
    onBackClick: () -> Unit
) {
    ConnectDogTopAppBar(
        titleRes = R.string.review_top_app_bar_title,
        navigationType = TopAppBarNavigationType.BACK,
        navigationIconContentDescription = "Back",
        onNavigationClick = { onBackClick() }
    )
}

@Composable
private fun ReviewContent(uiState: ReviewUiState) {
    val modifier = Modifier.padding(horizontal = 0.dp)
    when (uiState) {
        is ReviewUiState.Reviews -> {
            ReviewListContent(
                list = uiState.reviews,
                modifier = modifier
            )
        }

        else -> ReviewLoading(modifier = modifier)
    }
}

@Composable
fun ReviewListContent(
    list: List<Review>,
    modifier: Modifier
) {
    LazyColumn(modifier = modifier) {
        items(list.take(30)) {
            ReviewItemContent(review = it)
        }
    }
}

@Composable
fun ReviewLoading(modifier: Modifier) {
    val list = List(10) {
        Review(
            profileNum = 0,
            dogName = "멍멍이",
            userName = "츄",
            mainImage = "",
            date = "23.10.19(목)",
            location = "서울 강남구 -> 서울 도봉구",
            organization = "단체이름",
            content = "진짜 천사같은 아기와 하루를 함께해서 행복했습니다 너무 감사드려요 봉사 또 해야징 ><",
            contentImages = null
        )
    }
    LazyColumn(modifier = modifier) {
        items(list) {
            ReviewItemContent(review = it)
        }
    }
}

@Composable
private fun ReviewItemContent(
    review: Review,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        ConnectDogReview(review = review, type = ReviewType.REVIEW)
        Spacer(modifier = Modifier.height(16.dp))
        Divider(
            Modifier
                .height(1.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            color = Gray7
        )
        Spacer(modifier = Modifier.height(16.dp))
        IntermediatorInfo(review)
        Spacer(modifier = Modifier.height(16.dp))
        Divider(
            Modifier
                .height(8.dp)
                .fillMaxWidth(),
            color = Gray7
        )
    }
}

@Composable
private fun IntermediatorInfo(
    review: Review
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        NetworkImage(
            imageUrl = "",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Row {
                Text(
                    text = review.organization,
                    fontSize = 12.sp,
                    color = Gray1,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "프로필 보기",
                    fontSize = 12.sp,
                    color = Gray3,
                    fontWeight = FontWeight.Normal
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = review.dogName,
                    fontSize = 14.sp,
                    color = Gray1,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = review.location,
                    fontSize = 12.sp,
                    color = Gray3,
                    fontWeight = FontWeight.Normal
                )
            }
            Spacer(modifier = Modifier.height(7.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_clock), contentDescription = null, tint = Gray2)
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "text", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Gray2)
            }
        }
    }
}

@Preview
@Composable
private fun ReviewScreenPreview() {
    ConnectDogTheme {
        ReviewContent(uiState = ReviewUiState.Empty)
    }
}
