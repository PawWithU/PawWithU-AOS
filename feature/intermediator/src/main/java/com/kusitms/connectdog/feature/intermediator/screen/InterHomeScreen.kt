package com.kusitms.connectdog.feature.intermediator.screen

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.component.ConnectDogFilledButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogIntermediatorTopAppBar
import com.kusitms.connectdog.core.designsystem.component.NetworkImage
import com.kusitms.connectdog.core.designsystem.theme.Brown5
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.core.designsystem.theme.Typography
import com.kusitms.connectdog.core.model.IntermediatorManage
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.intermediator.R
import com.kusitms.connectdog.feature.intermediator.viewmodel.InterHomeViewModel

private val imageList = listOf(
    R.drawable.ic_recruit,
    R.drawable.ic_waiting,
    R.drawable.ic_progress,
    R.drawable.ic_complete
)

private val titleList = listOf(
    R.string.recruit,
    R.string.waiting,
    R.string.progress,
    R.string.complete
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InterHomeScreen(
    onNotificationClick: () -> Unit,
    onSettingClick: (UserType) -> Unit,
    onManageClick: (Int) -> Unit,
    onProfileClick: () -> Unit,
    onNavigateToCreateAnnouncementScreen: () -> Unit,
    viewModel: InterHomeViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchIntermediatorInfo()
    }
    Scaffold(
        topBar = {
            ConnectDogIntermediatorTopAppBar(
                onNotificationClick = onNotificationClick,
                onSettingClick = onSettingClick
            )
        }
    ) {
        Content(
            viewModel = viewModel,
            onManageClick = onManageClick,
            navigateToProfile = onProfileClick,
            navigateToCreateAnnouncementScreen = onNavigateToCreateAnnouncementScreen
        )
    }
}

@Composable
private fun Content(
    viewModel: InterHomeViewModel,
    navigateToCreateAnnouncementScreen: () -> Unit,
    navigateToProfile: () -> Unit,
    onManageClick: (Int) -> Unit
) {
    val recruitingCount = viewModel.recruitingCount.collectAsState()
    val waitingCount = viewModel.waitingCount.collectAsState()
    val progressingCount = viewModel.progressingCount.collectAsState()
    val completedCount = viewModel.completedCount.collectAsState()
    val cnt = listOf(
        recruitingCount.value,
        waitingCount.value,
        progressingCount.value,
        completedCount.value
    )

    val list = cnt.mapIndexedNotNull { index, value ->
        IntermediatorManage(
            image = imageList[index],
            title = titleList[index],
            value = value
        )
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        ProfileCard(viewModel, navigateToProfile)
        ManageBoard(
            list = list,
            onClick = onManageClick,
            onNavigateToCreateAnnouncementScreen = navigateToCreateAnnouncementScreen
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
private fun ProfileCard(
    viewModel: InterHomeViewModel,
    onProfileClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(178.dp)
            .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                .align(Alignment.Center)
        ) {
            Column {
                NetworkImage(
                    imageUrl = viewModel.profileImage.value,
                    modifier = Modifier.size(80.dp),
                    placeholder = painterResource(id = R.drawable.ic_default_intermediator)
                )
                Spacer(modifier = Modifier.height(18.dp))
                Row(
                    modifier = Modifier.wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = viewModel.intermediaryName.value,
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.White,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    ConnectDogFilledButton(
                        width = 54,
                        height = 16,
                        text = "프로필 보기",
                        padding = 1,
                        onClick = onProfileClick
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = viewModel.intro.value,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.widthIn(min = 0.dp, max = 220.dp),
                    lineHeight = 12.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_dog),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
private fun ManageBoard(
    list: List<IntermediatorManage>,
    onClick: (Int) -> Unit,
    onNavigateToCreateAnnouncementScreen: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brown5)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Text(
                "전체",
                modifier = Modifier.padding(start = 20.dp),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 18.sp
            )
            Text(
                text = if (list.all { it.value != null }) {
                    list.sumOf { it.value!! }.toString()
                } else {
                    ""
                } + "건",
                modifier = Modifier.padding(start = 5.dp),
                style = MaterialTheme.typography.titleLarge,
                color = PetOrange,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            itemsIndexed(list) { index, item ->
                ManageCard(
                    title = item.title,
                    painter = item.image,
                    value = if (item.value != null) item.value.toString() else ""
                ) { onClick(index) }
            }
        }
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ApplyButton(onClick = onNavigateToCreateAnnouncementScreen)
        }
    }
}

@Composable
private fun ApplyButton(onClick: () -> Unit) {
    val context = LocalContext.current
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.wrapContentSize(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(horizontal = 15.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(11.dp))
        Text(
            text = "공고 등록하기",
            color = Color.White,
            style = Typography.titleSmall,
            fontSize = 12.sp,
            modifier = Modifier.padding(vertical = 15.dp)
        )
    }
}

@Composable
private fun ManageCard(
    @StringRes title: Int,
    @DrawableRes painter: Int,
    value: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .size(width = 150.dp, height = 190.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, top = 20.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.titleSmall,
                color = Gray2
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${value}건",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = painter),
                    contentDescription = null
                )
            }
        }
    }
}
