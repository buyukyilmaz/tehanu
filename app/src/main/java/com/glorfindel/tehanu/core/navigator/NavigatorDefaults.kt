package com.glorfindel.tehanu.core.navigator

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.glorfindel.tehanu.core.TehanuDefaults

const val NAV_ARG_KEY = "navArg"
const val DEEPLINK_EXTRA_KEY = "deeplinkExtra"

val TehanuNavigatorComposition = compositionLocalOf<TehanuNavigator> { error("need DefaultNavHost") }
val TehanuNavHostControllerComposition = compositionLocalOf<NavHostController> { error("need DefaultNavHost") }

@Composable
@ReadOnlyComposable
fun tehanuNavigator() = TehanuNavigatorComposition.current

@Composable
@ReadOnlyComposable
fun tehanuNavHostController() = TehanuNavHostControllerComposition.current

fun NavGraphBuilder.screen(
    route: String,
    enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = TehanuDefaults.Navigator.enterTransition,
    exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = TehanuDefaults.Navigator.exitTransition,
    popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = TehanuDefaults.Navigator.popEnterTransition,
    popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = TehanuDefaults.Navigator.popExitTransition,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = "$route?$NAV_ARG_KEY={$NAV_ARG_KEY}&$DEEPLINK_EXTRA_KEY={$DEEPLINK_EXTRA_KEY}",
        arguments = listOf(navArgument(NAV_ARG_KEY) { nullable = true }, navArgument(DEEPLINK_EXTRA_KEY) { nullable = true }),
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        content = content
    )
}

fun NavGraphBuilder.nested(
    startDestination: String,
    route: String,
    enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = TehanuDefaults.Navigator.enterTransition,
    exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = TehanuDefaults.Navigator.exitTransition,
    popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = TehanuDefaults.Navigator.popEnterTransition,
    popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = TehanuDefaults.Navigator.popExitTransition,
    builder: NavGraphBuilder.() -> Unit
) {
    navigation(
        startDestination = startDestination,
        route = "$route?$NAV_ARG_KEY={$NAV_ARG_KEY}&$DEEPLINK_EXTRA_KEY={$DEEPLINK_EXTRA_KEY}",
        arguments = listOf(navArgument(NAV_ARG_KEY) { nullable = true }, navArgument(DEEPLINK_EXTRA_KEY) { nullable = true }),
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        builder = builder
    )
}

@Composable
fun DefaultNavHost(
    navHostController: NavHostController,
    startDestination: String,
    modifier: Modifier,
    enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = TehanuDefaults.Navigator.enterTransition,
    exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = TehanuDefaults.Navigator.exitTransition,
    popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = TehanuDefaults.Navigator.popEnterTransition,
    popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = TehanuDefaults.Navigator.popExitTransition,
    initializer: @Composable (() -> Unit)? = null,
    builder: NavGraphBuilder.() -> Unit
) {
    val tehanuNavigator = remember { TehanuNavigator(navHostController) }
    CompositionLocalProvider(
        TehanuNavigatorComposition provides tehanuNavigator,
        TehanuNavHostControllerComposition provides navHostController
    ) {
        initializer?.invoke()

        NavHost(
            navController = navHostController,
            startDestination = startDestination,
            modifier = modifier,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
            builder = builder
        )
    }
}
