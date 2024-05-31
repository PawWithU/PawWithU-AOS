package com.kusitms.connectdog.feature.intermediator.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.intermediator.screen.AnnouncementManageScreen
import com.kusitms.connectdog.feature.intermediator.screen.CreateApplicationDogScreen
import com.kusitms.connectdog.feature.intermediator.screen.CreateApplicationInfoScreen
import com.kusitms.connectdog.feature.intermediator.screen.InterHomeScreen
import com.kusitms.connectdog.feature.intermediator.screen.InterManagementRoute
import com.kusitms.connectdog.feature.intermediator.screen.InterProfileEditScreen
import com.kusitms.connectdog.feature.intermediator.screen.InterProfileScreen
import com.kusitms.connectdog.feature.intermediator.screen.ReviewScreen
import com.kusitms.connectdog.feature.intermediator.viewmodel.CreateApplicationViewModel

fun NavController.navigateInterHome() {
    navigate(IntermediatorRoute.route) {
        popUpTo(graph.id) {
            inclusive = true
        }
    }
}

fun NavController.navigateInterManagement(tabIndex: Int) {
    val route = "${IntermediatorRoute.management}?tabIndex=$tabIndex"
    navigate(route)
}

fun NavController.navigateInterProfile() {
    navigate(IntermediatorRoute.inter_profile)
}

fun NavController.navigateToCreateAnnouncementScreen() {
    navigate(IntermediatorRoute.create_announcement)
}

fun NavController.navigateToInterProfileEdit() {
    navigate(IntermediatorRoute.inter_profile_edit)
}

fun NavController.navigateToReview(id: Long) {
    val route = "${IntermediatorRoute.inter_review}?id=$id"
    navigate(route)
}

fun NavController.navigateToCreateDog() {
    navigate(IntermediatorRoute.create_application_dog)
}

fun NavController.navigateToAnnouncementManagement(postId: Long) {
    navigate("${IntermediatorRoute.announce_management}/$postId")
}

fun NavGraphBuilder.intermediatorNavGraph(
    imeHeight: Int,
    createApplicationViewModel: CreateApplicationViewModel,
    onBackClick: () -> Unit,
    onSettingClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit,
    onManagementClick: (Int) -> Unit,
    onNavigateToCreateAnnouncement: () -> Unit,
    onNavigateToInterProfileEdit: () -> Unit,
    onNavigateToReview: (Long, UserType) -> Unit,
    onNavigateToAnnouncementManagement: (Long) -> Unit,
    onNavigateToCreateDog: () -> Unit
) {
    composable(route = IntermediatorRoute.route) {
        InterHomeScreen(
            onNotificationClick = onNotificationClick,
            onSettingClick = onSettingClick,
            onManageClick = onManagementClick,
            onProfileClick = onProfileClick,
            onNavigateToCreateAnnouncementScreen = onNavigateToCreateAnnouncement
        )
    }

    composable(
        "${IntermediatorRoute.management}?tabIndex={tabIndex}",
        arguments = listOf(navArgument("tabIndex") { defaultValue = 0 })
    ) {
        InterManagementRoute(
            onBackClick = onBackClick,
            tabIndex = it.arguments?.getInt("tabIndex") ?: 0,
            onNavigateToReview = onNavigateToReview,
            onNavigateToAnnouncementManagement = onNavigateToAnnouncementManagement
        )
    }

    composable(route = IntermediatorRoute.inter_profile) {
        InterProfileScreen(
            onBackClick = onBackClick,
            onNavigateToInterProfileEdit = onNavigateToInterProfileEdit
        )
    }

    composable(route = IntermediatorRoute.create_announcement) {
        CreateApplicationInfoScreen(
            onBackClick = onBackClick,
            navigateToCreateDog = onNavigateToCreateDog,
            imeHeight = imeHeight,
            viewModel = createApplicationViewModel
        )
    }

    composable(route = IntermediatorRoute.inter_profile_edit) {
        InterProfileEditScreen(
            imeHeight = imeHeight,
            onBackClick = onBackClick
        )
    }

    composable(route = IntermediatorRoute.inter_review) {
        ReviewScreen()
    }

    composable(route = IntermediatorRoute.create_application_dog) {
        CreateApplicationDogScreen(
            imeHeight = imeHeight,
            onBackClick = onBackClick,
            viewModel = createApplicationViewModel
        )
    }

    composable(
        route = "${IntermediatorRoute.announce_management}/{postId}",
        arguments = listOf(navArgument("postId") { type = NavType.LongType })
    ) {
        AnnouncementManageScreen(
            postId = it.arguments!!.getLong("postId"),
            onBackClick = onBackClick,
            onIntermediatorProfileClick = {}
        )
    }
}

object IntermediatorRoute {
    const val route = "inter_home"
    const val management = "inter_management"
    const val inter_profile = "inter_profile"
    const val create_announcement = "create_announcement"
    const val inter_profile_edit = "inter_profile_edit"
    const val inter_review = "inter_review"
    const val create_application_dog = "create_application_dog"
    const val announce_management = "announcement_management"
}
