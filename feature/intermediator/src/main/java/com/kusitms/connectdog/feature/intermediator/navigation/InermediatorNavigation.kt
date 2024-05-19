package com.kusitms.connectdog.feature.intermediator.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kusitms.connectdog.feature.intermediator.screen.CreateApplicationDogScreen
import com.kusitms.connectdog.feature.intermediator.screen.CreateApplicationTransportScreen
import com.kusitms.connectdog.feature.intermediator.screen.InterHomeScreen
import com.kusitms.connectdog.feature.intermediator.screen.InterManagementRoute
import com.kusitms.connectdog.feature.intermediator.screen.InterProfileEditScreen
import com.kusitms.connectdog.feature.intermediator.screen.InterProfileScreen
import com.kusitms.connectdog.feature.intermediator.screen.ReviewScreen

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

fun NavGraphBuilder.intermediatorNavGraph(
    imeHeight: Int,
    onBackClick: () -> Unit,
    onSettingClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit,
    onManagementClick: (Int) -> Unit,
    onNavigateToCreateAnnouncement: () -> Unit,
    onNavigateToInterProfileEdit: () -> Unit,
    onNavigateToReview: (Long) -> Unit,
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
            tabIndex = it.arguments?.getInt("tabIndex") ?: 0
        )
    }

    composable(route = IntermediatorRoute.inter_profile) {
        InterProfileScreen(
            onBackClick = onBackClick,
            onNavigateToInterProfileEdit = onNavigateToInterProfileEdit
        )
    }

    composable(route = IntermediatorRoute.create_announcement) {
        CreateApplicationTransportScreen(
            onBackClick = onBackClick,
            navigateToCreateDog = onNavigateToCreateDog
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
            onBackClick = onBackClick
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
}
