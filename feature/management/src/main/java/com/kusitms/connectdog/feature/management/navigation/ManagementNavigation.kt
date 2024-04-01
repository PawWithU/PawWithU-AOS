package com.kusitms.connectdog.feature.management.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kusitms.connectdog.feature.management.ManagementRoute

fun NavController.navigateManagement(navOptions: NavOptions) {
    navigate(ManagementRoute.route, navOptions)
}

fun NavController.navigateCreateReview() {
    navigate(ManagementRoute.create_review)
}

fun NavGraphBuilder.managementNavGraph(
    onBackClick: () -> Unit,
    onNavigateToCreateReview: () -> Unit,
    onShowErrorSnackbar: (throwable: Throwable?) -> Unit
) {
    composable(route = ManagementRoute.route) {
        ManagementRoute(
            onBackClick,
            onNavigateToCreateReview,
            onShowErrorSnackbar
        )
    }

    composable(route = ManagementRoute.create_review) {
        CreateReviewScreen(
            onBackClick = onBackClick
        )
    }
}

object ManagementRoute {
    const val route = "management"
    const val create_review = "create_review"
}
