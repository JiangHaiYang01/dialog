package com.allens.dialog.util

import android.graphics.drawable.GradientDrawable
import android.view.View

/**
 *
 * @Description:
 * @author : jianghaiyang
 * @date: 2022/3/15 1:28 上午
 * @version: 1.0.0
 */
/**
 * 画圆角方法
 *
 * @param color      背景色
 * @param radius     圆角
 * @return
 */
internal fun View.radius(
    color: Int? = null,
    radius: Float
) {
    this.background = GradientDrawable().apply {
        cornerRadius = radius
        color?.let { this.setColor(it) }
    }
}

/**
 * 画圆角方法
 *
 * @param color      背景色
 * @param tl         左上
 * @param tl         右上
 * @param bl         右下
 * @param br         右sha
 * @return
 */
internal fun View.radius(
    color: Int? = null,
    tl: Float = 0f,
    tr: Float = 0f,
    bl: Float = 0f,
    br: Float = 0f,
) {
    this.background = GradientDrawable().apply {
        cornerRadii = floatArrayOf(tl, tl, tr, tr, bl, bl, br, br)
        color?.let { this.setColor(it) }
    }
}