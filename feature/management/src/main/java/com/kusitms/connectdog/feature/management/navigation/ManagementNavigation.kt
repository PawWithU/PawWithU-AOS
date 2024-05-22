package com.kusitms.connectdog.feature.management.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kusitms.connectdog.core.model.Application
import com.kusitms.connectdog.feature.management.screen.CreateReviewScreen
import com.kusitms.connectdog.feature.management.screen.ManagementRoute
import com.kusitms.connectdog.core.util.test

fun NavController.navigateManagement(navOptions: NavOptions) {
    navigate(ManagementRoute.route, navOptions)
}

fun NavController.navigateCreateReview(application: Application) {
    navigate("${ManagementRoute.create_review}/$application")
}

fun NavGraphBuilder.managementNavGraph(
    onBackClick: () -> Unit,
    onNavigateToCreateReview: (Application) -> Unit,
    onShowErrorSnackbar: (throwable: Throwable?) -> Unit
) {
    composable(route = ManagementRoute.route) {
        ManagementRoute(
            onBackClick,
            onNavigateToCreateReview,
            onShowErrorSnackbar
        )
    }

    composable(
        route = "${ManagementRoute.create_review}/{application}",
        arguments = listOf(
            navArgument("application") { type = NavType.StringType }
        )
    ) {
        val applicationJson = it.arguments?.getString("application")
        val application = test(applicationJson)
        CreateReviewScreen(
            onBackClick = onBackClick,
            application = application
        )
    }
}

object ManagementRoute {
    const val route = "management"
    const val create_review = "create_review"
}
