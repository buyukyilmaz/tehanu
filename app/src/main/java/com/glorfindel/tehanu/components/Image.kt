package com.glorfindel.tehanu.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.DefaultModelEqualityDelegate
import coil.compose.EqualityDelegate

object Image {
    @Composable
    @NonRestartableComposable
    fun Drawable(
        resId: Int,
        modifier: Modifier,
        contentDescription: String? = null,
        alignment: Alignment = Alignment.Center,
        contentScale: ContentScale = ContentScale.Fit,
        alpha: Float = DefaultAlpha,
        colorFilter: ColorFilter? = null
    ) {
        Image(
            modifier = modifier,
            painter = painterResource(resId),
            contentDescription = contentDescription,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter
        )
    }

    @Composable
    @NonRestartableComposable
    fun Bitmap(
        bitmap: ImageBitmap,
        modifier: Modifier,
        contentDescription: String? = null,
        alignment: Alignment = Alignment.Center,
        contentScale: ContentScale = ContentScale.Fit,
        alpha: Float = DefaultAlpha,
        colorFilter: ColorFilter? = null
    ) {
        Image(
            modifier = modifier,
            bitmap = bitmap,
            contentDescription = contentDescription,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter
        )
    }

    @Composable
    @NonRestartableComposable
    fun Url(
        url: String,
        modifier: Modifier = Modifier,
        contentDescription: String? = null,
        placeholder: Painter? = null,
        error: Painter? = null,
        fallback: Painter? = error,
        onLoading: ((AsyncImagePainter.State.Loading) -> Unit)? = null,
        onSuccess: ((AsyncImagePainter.State.Success) -> Unit)? = null,
        onError: ((AsyncImagePainter.State.Error) -> Unit)? = null,
        alignment: Alignment = Alignment.Center,
        contentScale: ContentScale = ContentScale.Fit,
        alpha: Float = DefaultAlpha,
        colorFilter: ColorFilter? = null,
        filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
        clipToBounds: Boolean = true,
        modelEqualityDelegate: EqualityDelegate = DefaultModelEqualityDelegate
    ) {
        AsyncImage(
            model = url,
            contentDescription = contentDescription,
            modifier = modifier,
            placeholder = placeholder,
            error = error,
            fallback = fallback,
            onLoading = onLoading,
            onSuccess = onSuccess,
            onError = onError,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter,
            filterQuality = filterQuality,
            clipToBounds = clipToBounds,
            modelEqualityDelegate = modelEqualityDelegate
        )
    }

    object Circular {
        @Composable
        @NonRestartableComposable
        fun Drawable(
            modifier: Modifier,
            imageResId: Int,
            background: Color = Color.Unspecified,
            borderWidth: Dp = 0.dp,
            borderColor: Color = Color.Unspecified,
            contentPadding: Dp = 0.dp,
            contentScale: ContentScale = ContentScale.Fit,
            colorFilter: ColorFilter? = null
        ) {
            Box(
                modifier =
                    modifier
                        .clip(CircleShape)
                        .background(background)
                        .border(borderWidth, borderColor, CircleShape)
                        .padding(contentPadding),
                contentAlignment = Alignment.Center
            ) {
                Drawable(
                    resId = imageResId,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = contentScale,
                    colorFilter = colorFilter
                )
            }
        }

        @Composable
        @NonRestartableComposable
        fun Url(
            modifier: Modifier,
            url: String,
            background: Color = Color.Unspecified,
            borderWidth: Dp = 0.dp,
            borderColor: Color = Color.Unspecified,
            contentPadding: Dp = 0.dp,
            contentScale: ContentScale = ContentScale.Fit,
            colorFilter: ColorFilter? = null
        ) {
            Box(
                modifier =
                    modifier
                        .clip(CircleShape)
                        .background(background)
                        .border(borderWidth, borderColor, CircleShape)
                        .padding(contentPadding),
                contentAlignment = Alignment.Center
            ) {
                Image.Url(
                    url = url,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = contentScale,
                    colorFilter = colorFilter
                )
            }
        }
    }
}
