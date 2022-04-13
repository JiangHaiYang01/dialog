package com.allens.dialog.util

import android.app.Activity
import android.graphics.Insets
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import androidx.annotation.NonNull


/**
 *
 * @Description:
 * @author : jianghaiyang
 * @date: 2022/3/15 11:37 下午
 * @version: 1.0.0
 */
internal class ScreenUtil {

    companion object {
        fun getScreen(@NonNull activity: Activity): ArrayList<Int> {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val windowMetrics = activity.windowManager.currentWindowMetrics
                val insets: Insets = windowMetrics.windowInsets
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
                arrayListOf(
                    windowMetrics.bounds.width() - insets.left - insets.right,
                    windowMetrics.bounds.height() - insets.bottom - insets.top
                )
            } else {
                val displayMetrics = DisplayMetrics()
                activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
                arrayListOf(
                    displayMetrics.widthPixels,
                    displayMetrics.heightPixels
                )
            }
        }
    }
}