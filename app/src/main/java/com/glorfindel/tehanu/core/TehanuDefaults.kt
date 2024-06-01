package com.glorfindel.tehanu.core

import android.util.Base64
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import com.glorfindel.tehanu.common.enums.Language
import com.glorfindel.tehanu.utils.DateUtils
import com.glorfindel.tehanu.visualTransformations.AmountTransformation
import com.glorfindel.tehanu.visualTransformations.GsmTransformationType

object TehanuDefaults {
    data object Common {
        var language: Language = Language.US
        var singleClickDelay: Long = 500
    }

    data object Date {
        var format = DateUtils.FORMAT_4
        var separator = "."
    }

    data object AnnotatedString {
        var startIdentifier = "{"
        var endIdentifier = "}"
    }

    data object Logger {
        var tag = "TEHANU_LOGGER"
        var showLog = false
    }

    data object Amount {
        var pattern: AmountTransformation.PatternType = AmountTransformation.PatternType.OTHER
        var fractionDigitsLength = 2
        var decimalDigitsLength = 9
    }

    data object Gsm {
        var type: GsmTransformationType = GsmTransformationType.DASH
    }

    data object Navigator {
        var encodeFlag = Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP
        var enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(300)) }
        var exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(300)) }
        var popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(300)) }
        var popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(300)) }
    }
}
