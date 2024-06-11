package com.kusitms.connectdog.feature.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.login.screen.EmailSearchResultScreen
import com.kusitms.connectdog.feature.login.screen.EmailSearchScreen
import com.kusitms.connectdog.feature.login.screen.LoginRoute
import com.kusitms.connectdog.feature.login.screen.NormalLoginScreen
import com.kusitms.connectdog.feature.login.screen.PasswordSearchAuthScreen
import com.kusitms.connectdog.feature.login.screen.PasswordSearchScreen

fun NavController.navigateToLoginRoute() {
    navigate(LoginRoute.route) {
        popUpTo(graph.id) {
            inclusive = true
        }
    }
}

fun NavController.navigateNormalLogin(userType: UserType) {
    navigate("${LoginRoute.normal_login}/$userType")
}

fun NavController.navigateEmailSearch(userType: UserType) {
    navigate("${LoginRoute.email_search}/$userType")
}

fun NavController.navigateEmailSearchComplete(email: String) {
    navigate("${LoginRoute.email_search_complete}/$email")
}

fun NavController.navigatePasswordSearchAuth(userType: UserType) {
    navigate("${LoginRoute.password_search_auth}/$userType")
}

fun NavController.navigatePasswordSearch(userType: UserType) {
    navigate("${LoginRoute.password_search}/$userType")
}

fun NavGraphBuilder.loginNavGraph(
    imeHeight: Int,
    finish: () -> Unit,
    onBackClick: () -> Unit,
    onNavigateToNormalLogin: (UserType) -> Unit,
    onNavigateToVolunteer: () -> Unit,
    onNavigateToIntermediatorHome: () -> Unit,
    onNavigateToSignup: (UserType) -> Unit,
    onNavigateToEmailSearch: (UserType) -> Unit,
    onNavigateToPasswordSearch: (UserType) -> Unit,
    onNavigateToEmailSearchComplete: (String) -> Unit,
    onNavigateToPasswordSearchAuth: (UserType) -> Unit,
    onNavigateToLoginRoute: () -> Unit,
    onSendMessage: (String) -> Unit,
    onVerifyCode: (String, (Boolean) -> Unit) -> Unit
) {
    composable(route = LoginRoute.route) {
        LoginRoute(
            finish,
            onNavigateToNormalLogin,
            onNavigateToSignup,
            onNavigateToVolunteer,
            onNavigateToIntermediatorHome,
            onNavigateToEmailSearch,
            onNavigateToPasswordSearchAuth
        )
    }

    composable(
        route = "${LoginRoute.normal_login}/{type}",
        arguments = listOf(
            navArgument("type") {
                type = NavType.EnumType(UserType::class.java)
            }
        )
    ) {
        NormalLoginScreen(
            onBackClick = onBackClick,
            userType = it.arguments!!.getSerializable("type") as UserType,
            onNavigateToSignUp = onNavigateToSignup,
            onNavigateToVolunteerHome = onNavigateToVolunteer,
            onNavigateToIntermediatorHome = onNavigateToIntermediatorHome,
            onNavigateToEmailSearch = onNavigateToEmailSearch,
            onNavigateToPasswordSearch = onNavigateToPasswordSearchAuth
        )
    }

    composable(
        route = "${LoginRoute.email_search}/{type}",
        arguments = listOf(
            navArgument("type") {
                type = NavType.EnumType(UserType::class.java)
            }
        )
    ) {
        EmailSearchScreen(
            imeHeight = imeHeight,
            onBackClick = onBackClick,
            navigateToCompleteScreen = onNavigateToEmailSearchComplete,
            userType = it.arguments!!.getSerializable("type") as UserType,
            onSendMessageClick = onSendMessage,
            onVerifyCodeClick = onVerifyCode
        )
    }

    composable(
        route = "${LoginRoute.email_search_complete}/{email}",
        arguments = listOf(
            navArgument("email") { type = NavType.StringType }
        )
    ) {
        it.arguments!!.getString("email")?.let { email ->
            EmailSearchResultScreen(
                onBackClick = onBackClick,
                email = email,
                navigateToLoginRoute = onNavigateToLoginRoute
            )
        }
    }

    composable(
        route = "${LoginRoute.password_search}/{type}",
        arguments = listOf(
            navArgument("type") {
                type = NavType.EnumType(UserType::class.java)
            }
        )
    ) {
        PasswordSearchScreen(
            onBackClick = onBackClick,
            userType = it.arguments!!.getSerializable("type") as UserType,
            imeHeight = imeHeight,
            navigateToLoginRoute = onNavigateToLoginRoute
        )
    }

    composable(
        route = "${LoginRoute.password_search_auth}/{type}",
        arguments = listOf(
            navArgument("type") {
                type = NavType.EnumType(UserType::class.java)
            }
        )
    ) {
        PasswordSearchAuthScreen(
            onBackClick = onBackClick,
            imeHeight = imeHeight,
            onNavigateToPasswordSearch = onNavigateToPasswordSearch,
            userType = it.arguments!!.getSerializable("type") as UserType
        )
    }
}

object LoginRoute {
    const val route = "login"
    const val normal_login = "normal_login"
    const val email_search = "email_search"
    const val password_search = "password_search"
    const val email_search_complete = "email_search_complete"
    const val password_search_auth = "password_search_auth"
}
