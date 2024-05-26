package com.kusitms.connectdog.feature.intermediator.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kusitms.connectdog.core.data.api.model.volunteer.NoticeDetailResponseItem
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.component.ConnectDogAlertDialog
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogDetailTopAppBar
import com.kusitms.connectdog.core.designsystem.component.ConnectDogInformationCard
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTag
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTagWithIcon
import com.kusitms.connectdog.core.designsystem.component.Loading
import com.kusitms.connectdog.core.designsystem.component.NetworkImage
import com.kusitms.connectdog.core.designsystem.component.button.ProfileButton
import com.kusitms.connectdog.core.designsystem.component.text.DetailInfo
import com.kusitms.connectdog.core.designsystem.component.text.TextWithIcon
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.feature.intermediator.state.AnnouncementManagementUiState
import com.kusitms.connectdog.feature.intermediator.viewmodel.AnnouncementManagementViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AnnouncementManageScreen(
    postId: Long,
    onBackClick: () -> Unit,
    onIntermediatorProfileClick: (Long) -> Unit,
    viewModel: AnnouncementManagementViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.initializePostId(postId)
    }

    val scrollState = rememberScrollState()
    val data by viewModel.announcementDetailState.collectAsStateWithLifecycle()

    var isDeleteDialogOpen by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ConnectDogDetailTopAppBar(
                onBackClick = onBackClick,
                onShareClick = {},
                onDeleteClick = { isDeleteDialogOpen = true },
                onEditClick = {}
            )
        },
        bottomBar = {
            BottomBar("모집중")
        }
    ) {
        when (data) {
            is AnnouncementManagementUiState.Loading -> Loading()
            is AnnouncementManagementUiState.AnnouncementDetail -> {
                Column(
                    modifier = Modifier.verticalScroll(scrollState)
                ) {
                    Spacer(modifier = Modifier.height(48.dp))
                    NetworkImage(
                        imageUrl = (data as AnnouncementManagementUiState.AnnouncementDetail).announcement.mainImage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )
                    Content(
                        detail = (data as AnnouncementManagementUiState.AnnouncementDetail).announcement,
                        onIntermediatorProfileClick = onIntermediatorProfileClick
                    )
                }
            }
        }
    }
    if (isDeleteDialogOpen) {
        ConnectDogAlertDialog(
            onDismissRequest = { isDeleteDialogOpen = false },
            titleRes = R.string.delete_dialog_title,
            descriptionRes = R.string.delete_dialog_description,
            okText = R.string.delete,
            cancelText = R.string.back,
            onClickOk = {
                viewModel.deleteAnnouncement()
                isDeleteDialogOpen = false
                onBackClick()
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Content(
    detail: NoticeDetailResponseItem,
    onIntermediatorProfileClick: (Long) -> Unit
) {
    val tabItems = listOf(
        "이동봉사 정보",
        "동물 정보",
        "모집자 정보"
    )

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val pagerState = rememberPagerState {
        tabItems.size
    }

    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        BasicInfo(detail = detail)
        Divider(
            Modifier
                .height(8.dp)
                .fillMaxWidth(),
            color = Gray7
        )
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabItems.forEachIndexed { index, title ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = {
                        selectedTabIndex = index
                    },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleSmall,
                            fontSize = 14.sp,
                            color = if (index == selectedTabIndex) MaterialTheme.colorScheme.primary else Gray2
                        )
                    }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) { index ->
            when (index) {
                0 -> VolunteerInfo(detail = detail)
                1 -> DogInfo(detail = detail)
                2 -> IntermediatorInfo(detail = detail, onClick = onIntermediatorProfileClick)
            }
        }
    }
}

@Composable
private fun BasicInfo(
    detail: NoticeDetailResponseItem
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 24.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = detail.dogName,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            ConnectDogTag(detail.postStatus)
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "${detail.departureLoc} → ${detail.arrivalLoc}",
            fontSize = 12.sp,
            color = Gray3
        )
        Spacer(modifier = Modifier.height(6.dp))
        TextWithIcon(iconId = R.drawable.ic_clock, text = detail.startDate, size = 14)
        Spacer(modifier = Modifier.height(8.dp))
        TextWithIcon(iconId = R.drawable.ic_clock, text = detail.pickUpTime, size = 14)
        Spacer(modifier = Modifier.height(17.dp))
        Row {
            ConnectDogTagWithIcon(
                iconId = R.drawable.ic_dog_size,
                backgroundColor = Gray7,
                contentColor = Gray3,
                text = detail.dogSize
            )
            Spacer(modifier = Modifier.width(4.dp))
            ConnectDogTagWithIcon(
                iconId = R.drawable.ic_kennel,
                backgroundColor = Gray7,
                contentColor = Gray3,
                text = if (detail.isKennel) {
                    stringResource(id = R.string.has_kennel)
                } else {
                    stringResource(id = R.string.has_not_kennel)
                }
            )
        }
    }
}

@Composable
fun VolunteerInfo(detail: NoticeDetailResponseItem) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 137.dp)
    ) {
        Text(
            text = "이동봉사 정보",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        DetailInfo("출발지", "${detail.departureLoc}")
        Spacer(modifier = Modifier.height(8.dp))
        DetailInfo("도착지", "${detail.arrivalLoc}")
        Spacer(modifier = Modifier.height(8.dp))
        DetailInfo("픽업 일시", detail.pickUpTime)
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = detail.content,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 22.sp
        )
    }
}

@Composable
fun DogInfo(detail: NoticeDetailResponseItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 137.dp)
    ) {
        Text(
            text = "강아지 정보",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        DetailInfo("이름", detail.dogName)
        Spacer(modifier = Modifier.height(8.dp))
        DetailInfo("동물 크기", detail.dogSize)
        Spacer(modifier = Modifier.height(20.dp))
        ConnectDogInformationCard(title = "특이사항", content = detail.specifics ?: "없습니다.")
    }
    Divider(
        Modifier
            .height(8.dp)
            .fillMaxWidth(),
        color = Gray7
    )
}

@Composable
fun IntermediatorInfo(
    detail: NoticeDetailResponseItem,
    onClick: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 137.dp)
    ) {
        Text(
            text = "이동봉사 중개",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
        ) {
            NetworkImage(
                imageUrl = detail.intermediaryProfileImage,
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                modifier = Modifier
                    .width(158.dp)
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                text = detail.intermediaryName,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            ProfileButton(
                modifier = Modifier
                    .width(100.dp)
                    .height(34.dp)
                    .align(Alignment.CenterVertically),
                onClick = { onClick(detail.intermediaryId.toLong()) }
            )
        }
    }
}

@Composable
private fun BottomBar(
    postStatus: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        ConnectDogBottomButton(content = "${postStatus}인 공고입니다", onClick = {}, enabled = false)
    }
}
