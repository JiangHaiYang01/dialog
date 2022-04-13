package com.allens.dialog.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes

/**
 * @author jhy
 * @description BaseDialog
 * @date 2021/10/12
 */
class CommonDialog : BaseDialog() {

    /* 布局ID **/
    @LayoutRes
    var layout: Int? = null

    /* 寻找布局 **/
    var viewCreate: ((View, Dialog?) -> Unit)? = null

    /* 动态添加View **/
    var contentView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutId = layout
        return when {
            layoutId != null -> {
                inflater.inflate(layoutId, container, false)
            }
            contentView != null -> {
                contentView
            }
            else -> {
                super.onCreateView(inflater, container, savedInstanceState)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreate?.invoke(view, dialog)
    }
}