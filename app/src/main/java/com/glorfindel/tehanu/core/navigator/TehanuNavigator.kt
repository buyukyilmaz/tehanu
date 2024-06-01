package com.glorfindel.tehanu.core.navigator

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.glorfindel.tehanu.core.TehanuDefaults
import com.glorfindel.tehanu.extension.base64Encode
import com.glorfindel.tehanu.infrastructure.Mapper

class TehanuNavigator(private val navHostController: NavHostController) {
    fun getNavHostController() = navHostController

    fun navigate(
        route: String,
        navArg: Any? = null,
        deeplinkExtra: DeeplinkExtra? = null,
        builder: NavOptionsBuilder.() -> Unit = {}
    ) {
        navHostController.navigate(createPath(route, navArg, deeplinkExtra), builder)
    }

    fun popAllAndNavigate(
        route: String,
        navArg: Any? = null,
        deeplinkExtra: DeeplinkExtra? = null
    ) {
        navHostController.navigate(createPath(route, navArg, deeplinkExtra)) {
            popUpTo(navHostController.graph.id) { inclusive = true }
        }
    }

    fun popAndNavigate(
        routeToNavigate: String,
        routeToPop: String,
        inclusive: Boolean = false,
        navArg: Any? = null,
        deeplinkExtra: DeeplinkExtra? = null
    ) {
        navHostController.navigate(createPath(routeToNavigate, navArg, deeplinkExtra)) {
            popUpTo(getRouteWithQuery(routeToPop)) { this.inclusive = inclusive }
        }
    }

    fun popUntilFirst() {
        navHostController.popBackStack(navHostController.graph.first().id, false)
    }

    fun pop(
        route: String,
        inclusive: Boolean = false,
        saveState: Boolean = false
    ) {
        navHostController.popBackStack(getRouteWithQuery(route), inclusive, saveState)
    }

    fun pop() {
        navHostController.popBackStack()
    }

    fun popParent() {
        val parent = navHostController.currentBackStackEntry?.destination?.parent ?: return
        getRouteWithoutQuery(parent.route)?.let {
            pop(it, true)
        }
    }

    fun currentRouteWithQuery() = navHostController.currentBackStackEntry?.destination?.route

    fun currentRouteWithoutQuery() = navHostController.currentBackStackEntry?.destination?.route?.substringBefore("?")

    fun getRouteWithQuery(routeWithoutQuery: String) = "$routeWithoutQuery?$NAV_ARG_KEY={$NAV_ARG_KEY}&$DEEPLINK_EXTRA_KEY={$DEEPLINK_EXTRA_KEY}"

    fun getRouteWithoutQuery(routeWithQuery: String?) = routeWithQuery?.substringBefore("?")

    private fun createPath(
        route: String,
        navArg: Any?,
        deeplinkExtra: DeeplinkExtra?
    ): String {
        val arg = if (navArg == null) null else Mapper().toJson(navArg).base64Encode(TehanuDefaults.Navigator.encodeFlag)
        val extra = if (deeplinkExtra == null) null else Mapper().toJson(deeplinkExtra).base64Encode(TehanuDefaults.Navigator.encodeFlag)
        return "$route?$NAV_ARG_KEY=$arg&$DEEPLINK_EXTRA_KEY=$extra"
    }
}
