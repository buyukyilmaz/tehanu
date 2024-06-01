package com.glorfindel.tehanu.core

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.DialogFragment
import com.glorfindel.tehanu.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class TehanuBottomSheet : BottomSheetDialogFragment() {
    protected open fun getHeightType(): HeightType = HeightType.WrapContent

    protected open fun useDefaultAnimation() = true

    protected open fun isDismissible() = true

    protected open fun isDraggable() = true

    protected open fun getDimAmount() = -1f

    protected open fun getStartMarginInDp() = 0

    protected open fun getEndMarginInDp() = 0

    protected open fun getTopMarginInDp() = 0

    protected open fun getBottomMarginInDp() = 0

    protected fun getAppCompatActivity() = requireActivity() as AppCompatActivity

    protected fun getTehanuActivity() = requireActivity() as TehanuActivity

    @Composable
    abstract fun TehanuContent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Tehanu_BottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent { TehanuContent() }
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        if (useDefaultAnimation()) {
            dialog?.window?.attributes?.windowAnimations = R.style.Tehanu_BottomsheetAnimation
        }
        isCancelable = isDismissible()
    }

    override fun onStart() {
        super.onStart()
        if (getDimAmount() != -1f) {
            dialog?.window?.setDimAmount(getDimAmount())
        }

        val bg = ColorDrawable(Color.TRANSPARENT)
        val density = Resources.getSystem().displayMetrics.density
        val marginStart = (density * getStartMarginInDp()).toInt()
        val marginEnd = (density * getEndMarginInDp()).toInt()
        val marginTop = (density * getTopMarginInDp()).toInt()
        val marginBottom = (density * getBottomMarginInDp()).toInt()
        val inset = InsetDrawable(bg, marginStart, marginTop, marginEnd, marginBottom)
        dialog?.window?.setBackgroundDrawable(inset)

        val view: FrameLayout = dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
        val behavior = BottomSheetBehavior.from(view)
        when (getHeightType()) {
            is HeightType.WrapContent -> {
                behavior.peekHeight = requireActivity().resources.displayMetrics.heightPixels
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.isDraggable = isDraggable()
            }

            is HeightType.FillHeight -> {
                view.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                behavior.peekHeight = requireActivity().resources.displayMetrics.heightPixels
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.isDraggable = isDraggable()
            }

            is HeightType.Fixed -> {
                behavior.peekHeight = (getHeightType() as HeightType.Fixed).heightInPx
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                behavior.isDraggable = isDraggable()
            }
        }
    }

    sealed class HeightType {
        data object WrapContent : HeightType()

        data object FillHeight : HeightType()

        data class Fixed(val heightInPx: Int) : HeightType()
    }
}
