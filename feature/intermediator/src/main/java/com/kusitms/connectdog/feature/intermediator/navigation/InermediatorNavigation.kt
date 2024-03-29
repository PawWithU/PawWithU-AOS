package com.kusitms.connectdog.feature.intermediator.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kusitms.connectdog.feature.intermediator.screen.InterManagementRoute
import com.kusitms.connectdog.feature.intermediator.screen.InterProfileScreen
import com.kusitms.connectdog.feature.intermediator.screen.IntermediatorHomeScreen

fun NavController.navigateIntermediatorHome() {
    navigate(IntermediatorRoute.route)
}

fun NavController.navigateInterManagement(tabIndex: Int) {
    Log.d("InterNavigation", "navigateInterManagement: tabIndex = $tabIndex")
    val route = "${IntermediatorRoute.management}?tabIndex=$tabIndex"
    navigate(route)
}

fun NavController.navigateInterProfile() {
    navigate(IntermediatorRoute.inter_profile)
}

fun NavGraphBuilder.intermediatorNavGraph(
    onBackClick: () -> Unit,
    onSettingClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit,
    onManagementClick: (Int) -> Unit
) {
    composable(route = IntermediatorRoute.route) {
        IntermediatorHomeScreen(
            onNotificationClick = onNotificationClick,
            onSettingClick = onSettingClick,
            onManageClick = onManagementClick,
            onProfileClick = onProfileClick
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
            onBackClick = onBackClick
        )
    }
}

object IntermediatorRoute {
    const val route = "inter_home"
    const val management = "inter_management"
    const val inter_profile = "inter_profile"
}
