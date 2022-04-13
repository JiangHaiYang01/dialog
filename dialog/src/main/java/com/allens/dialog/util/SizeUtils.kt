package com.allens.dialog.util

import android.content.res.Resources

/**
 * @author jhy
 * @description 格式钻花
 * @date 2021/10/15
 */
fun Float.dp2px(): Int {
    return (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}

fun Int.dp2px(): Int {
    return (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}

fun Float.px2dp(): Int {
    return (this / Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}

fun Int.px2dp(): Int {
    return (this / Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}

fun Float.sp2px(): Int {
    return (this * Resources.getSystem().displayMetrics.scaledDensity + 0.5f).toInt()
}

fun Int.sp2px(): Int {
    return (this * Resources.getSystem().displayMetrics.scaledDensity + 0.5f).toInt()
}

fun Float.px2sp(): Int {
    return (this / Resources.getSystem().displayMetrics.scaledDensity + 0.5f).toInt()
}

fun Int.px2sp(): Int {
    return (this / Resources.getSystem().displayMetrics.scaledDensity + 0.5f).toInt()
}


