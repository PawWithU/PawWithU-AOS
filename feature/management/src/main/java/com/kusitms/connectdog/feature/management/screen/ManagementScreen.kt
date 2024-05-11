package com.kusitms.connectdog.feature.management.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kusitms.connectdog.core.designsystem.component.AnnouncementItem
import com.kusitms.connectdog.core.designsystem.component.ConnectDogSecondaryButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.Empty
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.component.UiState
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.core.model.Application
import com.kusitms.connectdog.feature.management.R
import com.kusitms.connectdog.feature.management.component.MyApplicationBottomSheet
import com.kusitms.connectdog.feature.management.state.ApplicationUiState
import com.kusitms.connectdog.feature.management.viewmodel.ManagementViewModel
import kotlinx.coroutines.launch

private const val TAG = "ManagementScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ManagementRoute(
    onBackClick: () -> Unit,
    onNavigateToCreateReview: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    viewModel: ManagementViewModel = hiltViewModel()
) {
    viewModel.refreshWaitingApplications()

    val pendingUiState by viewModel.waitingUiState.collectAsStateWithLifecycle()
    val inProgressUiState by viewModel.progressUiState.collectAsStateWithLifecycle()
    val completedUiState by viewModel.completedUiState.collectAsStateWithLifecycle()

    val volunteer by viewModel.volunteer.collectAsStateWithLifecycle()
    val selectedApplication by viewModel.selectedApplication.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }

    val deleteDataState by viewModel.deleteDataUiState.collectAsState()
    UiState(dataUiState = deleteDataState) {
        viewModel.refreshWaitingApplications()
    }

    Column {
        ConnectDogTopAppBar(
            titleRes = null,
            navigationType = TopAppBarNavigationType.MANAGEMENT,
            navigationIconContentDescription = "Navigation icon",
            onNavigationClick = onBackClick
        )
        ManagementScreen(
            firstContent = {
                PendingApproval(pendingUiState) { application ->
                    viewModel.getVolunteerInfo(application.applicationId!!)
                    viewModel.updateSelectedApplication(application)
                    isSheetOpen = true
                }
            },
            secondContent = { InProgress(inProgressUiState) },
            thirdContent = { Completed(completedUiState, onClickReview = onNavigateToCreateReview) }
        )
    }

    if (isSheetOpen && volunteer != null && selectedApplication != null) {
        MyApplicationBottomSheet(
            sheetState = sheetState,
            application = selectedApplication!!,
            volunteer = volunteer!!,
            onDismissRequest = { isSheetOpen = false },
            onDeleteClick = viewModel::deleteMyApplication
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ManagementScreen(
    firstContent: @Composable () -> Unit,
    secondContent: @Composable () -> Unit,
    thirdContent: @Composable () -> Unit
) {
    val tabItems = listOf(
        stringResource(id = R.string.pending_approval),
        stringResource(id = R.string.inProgress),
        stringResource(id = R.string.completed)
    )

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            val pagerState = rememberPagerState()
            val coroutineScope = rememberCoroutineScope()
            TabRow(
                selectedTabIndex = pagerState.currentPage
            ) {
                tabItems.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleSmall,
                                fontSize = 14.sp,
                                color = if (pagerState.currentPage == index) MaterialTheme.colorScheme.primary else Gray2
                            )
                        }
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                count = tabItems.size,
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Top
            ) { index ->
                when (index) {
                    0 -> firstContent()
                    1 -> secondContent()
                    2 -> thirdContent()
                }
            }
        }
    }
}

@Composable
private fun PendingApproval(
    uiState: ApplicationUiState,
    onClick: (Application) -> Unit
) {
    when (uiState) {
        is ApplicationUiState.Applications -> {
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp)
            ) {
                items(uiState.applications) {
                    PendingContent(application = it, onClick = onClick)
                }
            }
            Spacer(modifier = Modifier.height(80.dp))
        }

        is ApplicationUiState.Empty -> {
            Empty(titleRes = R.string.no_pending, descriptionRes = R.string.no_description)
        }

        is ApplicationUiState.Loading -> Loading()
    }
}

@Composable
private fun InProgress(
    uiState: ApplicationUiState
) {
    when (uiState) {
        is ApplicationUiState.Applications -> {
            LazyColumn(verticalArrangement = Arrangement.Top) {
                items(uiState.applications) {
                    InProgressContent(application = it)
                }
            }
        }

        is ApplicationUiState.Empty -> {
            Empty(titleRes = R.string.no_progressing, descriptionRes = R.string.no_description)
        }

        is ApplicationUiState.Loading -> Loading()
    }
}

@Composable
private fun Completed(
    uiState: ApplicationUiState,
    onClickReview: () -> Unit
) {
    when (uiState) {
        is ApplicationUiState.Applications -> {
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.applications) {
                    CompletedContent(
                        application = it,
                        onClickReview = onClickReview
                    )
                }
            }
        }

        is ApplicationUiState.Empty -> {
            Empty(titleRes = R.string.no_completed, descriptionRes = R.string.no_description)
        }

        is ApplicationUiState.Loading -> Loading()
    }
}

@Composable
private fun PendingContent(application: Application, onClick: (Application) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            AnnouncementItem(
                imageUrl = application.imageUrl,
                dogName = application.dogName!!,
                location = application.location,
                isKennel = application.hasKennel,
                dogSize = application.dogSize!!,
                date = application.date,
                pickUpTime = application.pickUpTime!!
            )
            OutlinedButton(modifier = Modifier.padding(top = 20.dp)) {
                onClick(application)
            }
        }
        Divider(thickness = 8.dp, color = Gray7)
    }
}

@Composable
private fun InProgressContent(application: Application) {
//    ListForUserItem(
//        modifier = Modifier.padding(20.dp),
//        imageUrl = application.imageUrl,
//        location = application.location,
//        date = application.date,
//        organization = application.organization,
//        hasKennel = application.hasKennel
//    )
    Divider(thickness = 8.dp, color = Gray7)
}

@Composable
private fun CompletedContent(
    application: Application,
    onClickReview: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            AnnouncementItem(
                imageUrl = application.imageUrl,
                dogName = application.dogName!!,
                location = application.location,
                isKennel = application.hasKennel,
                dogSize = application.dogSize!!,
                date = application.date,
                pickUpTime = application.pickUpTime!!
            )
            Spacer(modifier = Modifier.size(20.dp))
            ReviewButton(
                modifier = Modifier.height(40.dp),
                hasReview = application.reviewId != null,
                onClickReview = onClickReview
            )
        }
        Divider(thickness = 8.dp, color = Gray7)
    }
}

@Composable
private fun OutlinedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ConnectDogSecondaryButton(
        modifier = modifier,
        contentRes = R.string.check_my_appliance
    ) { onClick() }
}

@Composable
private fun ReviewButton(
    modifier: Modifier = Modifier,
    hasReview: Boolean,
    onClickReview: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .border(
                shape = RoundedCornerShape(6.dp),
                color = MaterialTheme.colorScheme.outline,
                width = 1.dp
            )
            .clickable(enabled = hasReview) { onClickReview() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.create_review),
            style = MaterialTheme.typography.titleSmall,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = if (!hasReview) Gray4 else Gray1
        )
    }
}

@Composable
private fun Loading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
private fun CompletedContentPreview() {
    val application = Application("", "이동봉사 위치", "YY.mm.dd(요일)", "단체이름", false, 0, 0)
    ConnectDogTheme {
        CompletedContent(application = application, onClickReview = { })
    }
}
