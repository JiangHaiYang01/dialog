package com.allens.dialog.dialog

import android.content.Context
import android.view.View
import android.widget.TextView
import com.allens.dialog.annotation.DialogType

/**
 *
 * @Description:
 * @author : jianghaiyang
 * @date: 2022/4/10 20:44
 * @version: 1.0.0
 */
data class Wrapper(
    var info: String? = null,
    @DialogType var type: Int = DialogType.ITEM,
    var textSize: Float? = null,
    var color: Int? = null,
    var backgroundColor: Int? = null,
    var height: Int? = null,
    var view: ((Context?) -> View)? = null,
    var dismiss: Boolean = true,
    var singleClickListener: (() -> Unit)? = null,
    var longClickListener: (() -> Unit)? = null,
    var theme: ((TextView) -> Unit)? = null
)