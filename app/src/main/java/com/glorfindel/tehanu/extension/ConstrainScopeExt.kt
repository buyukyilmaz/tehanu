package com.glorfindel.tehanu.extension

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainScope
import androidx.constraintlayout.compose.ConstraintLayoutBaseScope
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility

fun ConstrainScope.visibleIf(visible: Boolean) {
    visibility = if (visible) Visibility.Visible else Visibility.Gone
}

fun ConstrainScope.goneIf(gone: Boolean) {
    visibility = if (gone) Visibility.Gone else Visibility.Visible
}

fun ConstrainScope.fillWidth() {
    width = Dimension.fillToConstraints
}

fun ConstrainScope.wrapContentWidth() {
    width = Dimension.wrapContent
}

fun ConstrainScope.width(dp: Dp) {
    width = Dimension.value(dp)
}

fun ConstrainScope.fillHeight() {
    height = Dimension.fillToConstraints
}

fun ConstrainScope.wrapContentHeight() {
    height = Dimension.wrapContent
}

fun ConstrainScope.height(dp: Dp) {
    height = Dimension.value(dp)
}

fun ConstrainScope.start(
    anchor: ConstraintLayoutBaseScope.VerticalAnchor = parent.start,
    margin: Dp = 0.dp,
    goneMargin: Dp = 0.dp
) {
    start.linkTo(anchor, margin, goneMargin)
}

fun ConstrainScope.end(
    anchor: ConstraintLayoutBaseScope.VerticalAnchor = parent.end,
    margin: Dp = 0.dp,
    goneMargin: Dp = 0.dp
) {
    end.linkTo(anchor, margin, goneMargin)
}

fun ConstrainScope.top(
    anchor: ConstraintLayoutBaseScope.HorizontalAnchor = parent.top,
    margin: Dp = 0.dp,
    goneMargin: Dp = 0.dp
) {
    top.linkTo(anchor, margin, goneMargin)
}

fun ConstrainScope.bottom(
    anchor: ConstraintLayoutBaseScope.HorizontalAnchor = parent.bottom,
    margin: Dp = 0.dp,
    goneMargin: Dp = 0.dp
) {
    bottom.linkTo(anchor, margin, goneMargin)
}
