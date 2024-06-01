package com.glorfindel.tehanu.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.glorfindel.tehanu.core.TehanuDefaults
import com.glorfindel.tehanu.core.navigator.DEEPLINK_EXTRA_KEY
import com.glorfindel.tehanu.core.navigator.DeeplinkExtra
import com.glorfindel.tehanu.core.navigator.NAV_ARG_KEY
import com.glorfindel.tehanu.infrastructure.Mapper

inline fun <reified T> NavBackStackEntry.toNavArg(): T? {
    val arg = arguments?.getString(NAV_ARG_KEY) ?: return null
    return Mapper().toObject(arg.base64Decode(TehanuDefaults.Navigator.encodeFlag), T::class.java)
}

fun NavBackStackEntry.toDeeplinkExtra(): DeeplinkExtra? {
    val deeplinkExtra = arguments?.getString(DEEPLINK_EXTRA_KEY) ?: return null
    return Mapper().toObject(deeplinkExtra.base64Decode(TehanuDefaults.Navigator.encodeFlag), DeeplinkExtra::class.java)
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navHostController: NavHostController): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry =
        remember(this) {
            navHostController.getBackStackEntry(navGraphRoute)
        }
    return viewModel(parentEntry)
}
