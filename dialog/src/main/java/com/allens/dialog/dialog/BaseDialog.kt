package com.allens.dialog.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.FloatRange
import androidx.annotation.StyleRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.allens.dialog.annotation.SizeFrom
import com.allens.dialog.util.ScreenUtil
import com.allens.dialog.util.radius

/**
 * @author jhy
 * @description BaseDialog
 * @date 2021/10/12
 */
abstract class BaseDialog : DialogFragment() {

    companion object {
        private const val TAG = "BaseDialog"
    }

    /* 屏幕高度 **/
    private var screenHeight = 0

    /* 屏幕宽度 **/
    private var screenWidth = 0

    /* 设置透明度 **/
    @FloatRange(from = 0.0, to = 1.0)
    var alpha: Float? = null

    /* 设置黑暗度（Dialog 窗口背景的黑暗度） **/
    @FloatRange(from = 0.0, to = 1.0)
    var dimAmount: Float? = null

    /* 设置Dialog圆角 **/
    var radius: Float? = null

    /* 点击物理返回键dialog不消失 **/
    var cancelable: Boolean? = null

    /* 点击屏幕dialog不消失 **/
    var canceledOnTouchOutside: Boolean? = null

    /* 返回 **/
    var back: (() -> Unit)? = null

    /* 位置 **/
    var gravity: Int? = null

    /* target **/
    var target: String? = null

    /* 高度模式 **/
    private var heightMode = SizeFrom.SPECIFY

    /* 高度 **/
    var height: Int? = null
        set(value) {
            field = value
            heightMode = SizeFrom.SPECIFY
        }

    /* 高度比例 **/
    @FloatRange(from = 0.0, to = 1.0)
    var heightScale: Float? = null
        set(value) {
            field = value
            heightMode = SizeFrom.SCALE
        }

    /* 宽度模式 **/
    private var widthMode = SizeFrom.SPECIFY

    /* 宽度 **/
    var width: Int? = null
        set(value) {
            field = value
            widthMode = SizeFrom.SPECIFY
        }

    /* 宽度比例 **/
    @FloatRange(from = 0.0, to = 1.0)
    var widthScale: Float? = null
        set(value) {
            field = value
            widthMode = SizeFrom.SCALE
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screen = ScreenUtil.getScreen(requireActivity())
        Log.i(TAG, "screen:$screen")
        screenWidth = screen[0]
        screenHeight = screen[1]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                back?.invoke()
            }
            false
        }

        val background = view.background
        val color: Int? = if (background is ColorDrawable) {
            background.color
        } else {
            null
        }
        radius?.let { view.radius(radius = it, color = color) }
        cancelable?.let { dialog?.setCancelable(it) }
        canceledOnTouchOutside?.let { dialog?.setCanceledOnTouchOutside(it) }
    }

    override fun onStart() {
        super.onStart()
        val window = dialog?.window ?: return
        window.apply {
            this.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            this.attributes = this.attributes.apply {
                this.width = getWith()
                this.height = getHeight()
                this@BaseDialog.also { dialog ->
                    dialog.gravity?.let { this.gravity = it }
                    dialog.alpha?.let { this.alpha = it }
                    dialog.dimAmount?.let { this.dimAmount = it }
                }
            }
        }
    }

    open fun show(context: FragmentActivity) {
        if (isShowing()) return
        show(context.supportFragmentManager, target)
    }

    /**
     * 是否正在显示
     */
    fun isShowing(): Boolean = dialog?.isShowing == true

    /**
     * 设置动画
     */
    fun setWindowAnimations(@StyleRes resId: Int) = apply {
        dialog?.window?.setWindowAnimations(resId)
    }

    private fun getHeight(): Int {
        val scale = heightScale
        val height = height
        return when {
            heightMode == SizeFrom.SCALE && scale != null -> {
                (scale * screenHeight).toInt()
            }
            heightMode == SizeFrom.SPECIFY && height != null -> {
                height
            }
            else -> ViewGroup.LayoutParams.WRAP_CONTENT
        }
    }

    private fun getWith(): Int {
        val scale = widthScale
        val width = width
        return when {
            widthMode == SizeFrom.SCALE && scale != null -> {
                (scale * screenWidth).toInt()
            }
            widthMode == SizeFrom.SPECIFY && width != null -> {
                width
            }
            else -> ViewGroup.LayoutParams.WRAP_CONTENT
        }
    }
}