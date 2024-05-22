package com.kusitms.connectdog.feature.mypage.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.data.api.model.volunteer.BookmarkResponseItem
import com.kusitms.connectdog.core.designsystem.component.AnnouncementContent
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.feature.mypage.R
import com.kusitms.connectdog.feature.mypage.viewmodel.MyPageViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BookmarkScreen(
    onBackClick: () -> Unit,
    onDetailClick: (Long) -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val bookmarkItem by viewModel.bookmark.observeAsState(null)

    LaunchedEffect(Unit) {
        viewModel.fetchBookmark()
    }

    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.bookmark,
                navigationType = TopAppBarNavigationType.BACK,
                onNavigationClick = onBackClick
            )
        }
    ) {
        bookmarkItem?.let { Content(it, onDetailClick) }
    }
}

@Composable
private fun Content(
    item: List<BookmarkResponseItem>,
    onDetailClick: (Long) -> Unit
) {
    if (item.isEmpty()) {
        // TODO 저장된 공고가 비어있는 경우 표시될 UI 추가
    } else {
        LazyColumn(
            modifier = Modifier.padding(top = 48.dp),
            verticalArrangement = Arrangement.Top
        ) {
            items(item) {
                val data = it.toData()
                AnnouncementContent(
                    postId = data.postId,
                    imageUrl = data.imageUrl,
                    dogName = data.dogName,
                    location = data.location,
                    isKennel = data.isKennel,
                    dogSize = data.dogSize,
                    date = data.date,
                    pickUpTime = data.pickUpTime,
                    onClick = onDetailClick
                )
            }
        }
    }
}

@Preview
@Composable
private fun Test() {
    ConnectDogTheme {
        BookmarkScreen(onBackClick = {}, {})
    }
}
