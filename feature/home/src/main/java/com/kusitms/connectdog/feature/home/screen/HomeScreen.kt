package com.kusitms.connectdog.feature.home.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kusitms.connectdog.core.designsystem.component.BannerGuideline
import com.kusitms.connectdog.core.designsystem.component.ConnectDogReview
import com.kusitms.connectdog.core.designsystem.component.NetworkImage
import com.kusitms.connectdog.core.designsystem.component.ReviewType
import com.kusitms.connectdog.core.designsystem.component.TextWithIcon
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray5
import com.kusitms.connectdog.core.model.AnnouncementHome
import com.kusitms.connectdog.core.model.Review
import com.kusitms.connectdog.feature.home.HomeViewModel
import com.kusitms.connectdog.feature.home.R
import com.kusitms.connectdog.feature.home.state.AnnouncementUiState
import com.kusitms.connectdog.feature.home.state.ReviewUiState
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun HomeRoute(
    onNavigateToSearch: () -> Unit,
    onNavigateToFilterSearch: () -> Unit,
    onNavigateToReview: () -> Unit,
    onNavigateToDetail: (Long) -> Unit,
    onNavigateToNotification: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    finish: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val announcementUiState by viewModel.announcementUiState.collectAsStateWithLifecycle()
    val reviewUiState by viewModel.reviewUiState.collectAsStateWithLifecycle()

    BackHandler { finish() }

    // 에러 발생할 때마다 에러 스낵바 표시
    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { throwable -> onShowErrorSnackBar(throwable) }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            onClickSearch = onNavigateToFilterSearch,
            onNotificationClick = onNavigateToNotification
        )
        HomeScreen(
            announcementUiState = announcementUiState,
            reviewUiState = reviewUiState,
            onNavigateToSearch = onNavigateToSearch,
            onNavigateToReview = onNavigateToReview,
            onNavigateToDetail = onNavigateToDetail
        )
    }
}

@Composable
private fun HomeScreen(
    announcementUiState: AnnouncementUiState,
    reviewUiState: ReviewUiState,
    onNavigateToSearch: () -> Unit,
    onNavigateToReview: () -> Unit,
    onNavigateToDetail: (Long) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
    ) {
        BannerGuideline(onNavigateToSearch)
        MoveContent(onClick = { onNavigateToSearch() }, titleRes = R.string.home_navigate_search)
        AnnouncementContent(announcementUiState, onClick = onNavigateToDetail)
        MoveContent(onClick = { onNavigateToReview() }, titleRes = R.string.home_navigate_review)
        ReviewContent(uiState = reviewUiState)
        Spacer(modifier = Modifier.height(90.dp))
    }
}

@Composable
private fun TopAppBar(
    onClickSearch: () -> Unit,
    onNotificationClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 16.dp)
    ) {
        SearchBar(
            onClick = onClickSearch,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(20.dp))
        IconButton(onClick = onNotificationClick) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Navigate to Search"
            )
        }
    }
}

@Composable
private fun SearchBar(
    onClick: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .height(50.dp)
            .border(
                width = 1.dp,
                color = Gray5,
                shape = RoundedCornerShape(90.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.CenterStart
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 20.dp)
                .size(24.dp),
            imageVector = Icons.Filled.Search,
            tint = Gray3,
            contentDescription = "Navigate to Search"
        )
        Text(
            modifier = Modifier.padding(start = 52.dp),
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        color = Gray1,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                ) {
                    append(stringResource(id = R.string.search_bar_title_1))
                }
                withStyle(
                    SpanStyle(
                        color = Gray3,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    )
                ) {
                    append(stringResource(id = R.string.search_bar_title_2))
                }
            },
            lineHeight = 15.sp
        )
    }
}

@Composable
private fun StatisticBanner(modifier: Modifier) {
    Column(horizontalAlignment = Alignment.End, modifier = modifier) {
        Row(
            modifier = Modifier.padding(end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_info),
                contentDescription = "info icon",
                modifier = Modifier.padding(2.dp),
                tint = Gray3
            )
            Text(
                text = stringResource(id = R.string.home_counting_guide),
                style = MaterialTheme.typography.labelMedium,
                color = Gray3
            )
        }
        Spacer(modifier = Modifier.size(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Divider(
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .height(80.dp)
                    .width(1.dp)
                    .align(Alignment.Center)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
            ) {
                StatisticInfoItem(
                    modifier = Modifier.weight(1f),
                    number = "105",
                    descriptionRes = R.string.home_need_move_description,
                    painter = painterResource(id = R.drawable.img_man_dog)
                )
                StatisticInfoItem(
                    modifier = Modifier.weight(1f),
                    number = "22",
                    descriptionRes = R.string.home_moved_description,
                    painter = painterResource(id = R.drawable.img_woman)
                )
            }
        }
    }
}

@Composable
private fun StatisticInfoItem(
    modifier: Modifier = Modifier,
    number: String = "0",
    descriptionRes: Int,
    painter: Painter
) {
    Row {
        Column(horizontalAlignment = Alignment.Start) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = number,
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = stringResource(id = R.string.home_dog_unit),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 2.dp)
                )
            }
            Text(
                text = stringResource(id = descriptionRes),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Image(painter = painter, contentDescription = "mandog")
    }
}

@Composable
fun MoveContent(
    onClick: () -> Unit,
    titleRes: Int
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 20.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 18.sp
        )
        IconButton(onClick = { onClick() }) {
            Icon(
                painter = painterResource(id = com.kusitms.connectdog.core.designsystem.R.drawable.ic_right_arrow),
                contentDescription = "move to another screen",
                modifier = Modifier.size(24.dp),
                tint = Gray2
            )
        }
    }
}

@Composable
private fun AnnouncementContent(uiState: AnnouncementUiState, onClick: (Long) -> Unit) {
    val modifier = Modifier.padding(horizontal = 20.dp)
    when (uiState) {
        is AnnouncementUiState.Announcements -> {
            AnnouncementListContent(
                list = uiState.announcementHomes,
                modifier = modifier,
                arrangement = Arrangement.spacedBy(12.dp),
                onClick = onClick
            )
        }

        else -> AnnouncementLoading(
            modifier = modifier,
            arrangement = Arrangement.spacedBy(12.dp)
        )
    }
}

@Composable
private fun ReviewContent(uiState: ReviewUiState) {
    val modifier = Modifier.padding(horizontal = 20.dp)
    when (uiState) {
        is ReviewUiState.Reviews -> {
            ReviewListContent(
                list = uiState.reviews,
                modifier = modifier,
                arrangement = Arrangement.spacedBy(12.dp)
            )
        }

        else -> ReviewLoading(modifier = modifier, arrangement = Arrangement.spacedBy(12.dp))
    }
}

@Composable
fun AnnouncementListContent(
    list: List<AnnouncementHome>,
    modifier: Modifier,
    arrangement: Arrangement.Horizontal,
    onClick: (Long) -> Unit
) {
    LazyRow(horizontalArrangement = arrangement, modifier = modifier) {
        items(list.take(10)) {
            AnnouncementCardContent(announcementHome = it, onClick = { onClick(it.postId.toLong()) })
        }
    }
}

@Composable
fun AnnouncementLoading(
    modifier: Modifier,
    arrangement: Arrangement.Horizontal
) {
    val list = List(4) {
        AnnouncementHome("", "이동봉사 위치", "YY.mm.dd(요일)", -1, "", "")
    }
    LazyRow(horizontalArrangement = arrangement, modifier = modifier) {
        items(list) {
            AnnouncementCardContent(announcementHome = it, onClick = {})
        }
    }
}

@Composable
private fun ReviewListContent(
    list: List<Review>,
    modifier: Modifier,
    arrangement: Arrangement.Horizontal
) {
    LazyRow(horizontalArrangement = arrangement, modifier = modifier) {
        items(list.take(10)) {
            ReviewCardContent(review = it)
        }
    }
}

@Composable
private fun ReviewLoading(modifier: Modifier, arrangement: Arrangement.Horizontal) {
    val list = List(4) {
        Review(
            profileNum = 0,
            dogName = "멍멍이",
            userName = "츄",
            date = "23.10.19(목)",
            location = "서울 강남구 -> 서울 도봉구",
            organization = "단체이름",
            content = "진짜 천사같은 아기와 하루를 함께해서 행복했습니다 너무 감사드려요 봉사 또 해야징 ><",
            contentImages = null,
            mainImage = ""
        )
    }
    LazyRow(horizontalArrangement = arrangement, modifier = modifier) {
        items(list) {
            ReviewCardContent(review = it)
        }
    }
}

@Composable
private fun AnnouncementCardContent(
    announcementHome: AnnouncementHome,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .width(150.dp)
            .clickable { onClick() }
    ) {
        NetworkImage(
            imageUrl = announcementHome.imageUrl,
            placeholder = ColorPainter(MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier
                .size(150.dp)
                .shadow(shape = RoundedCornerShape(12.dp), elevation = 1.dp)
        )
        Text(
            text = announcementHome.dogName,
            maxLines = 2,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 1.dp, top = 10.dp, bottom = 8.dp)
        )
        Text(
            text = announcementHome.location,
            color = Gray3,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextWithIcon(text = announcementHome.date.substringBefore(" "), iconId = R.drawable.ic_clock)
        Spacer(modifier = Modifier.height(5.dp))
        TextWithIcon(text = announcementHome.pickUpTime, iconId = R.drawable.ic_clock)
    }
}

@Composable
private fun ReviewCardContent(
    review: Review
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)
    ) {
        ConnectDogReview(review = review, modifier = Modifier.width(272.dp), type = ReviewType.HOME)
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    ConnectDogTheme {
        Column(modifier = Modifier.background(Color.White)) {
            TopAppBar(onClickSearch = {}, onNotificationClick = {})
            HomeScreen(
                announcementUiState = AnnouncementUiState.Empty,
                reviewUiState = ReviewUiState.Empty,
                {},
                {},
                {}
            )
        }
    }
}

// @Preview
// @Composable
// private fun AnnouncementPreview() {
//    ConnectDogTheme {
//        AnnouncementCardContent(
//            announcement = Announcement(
//                "",
//                "서울시 강남구 -> 서울시 도봉구",
//                "23.10.19(수)",
//                "단체이름이름",
//                true,
//                -1
//            )
//        )
//    }
// }
