package com.allens.dialog.annotation

import androidx.annotation.IntDef

/**
 *
 * @Description:
 * @author : jianghaiyang
 * @date: 2022/3/25 00:11
 * @version: 1.0.0
 */
@IntDef(DialogType.TITLE, DialogType.CANCEL, DialogType.ITEM)
internal annotation class DialogType {
    companion object {
        const val TITLE = 1
        const val CANCEL = 2
        const val ITEM = 3
    }
}