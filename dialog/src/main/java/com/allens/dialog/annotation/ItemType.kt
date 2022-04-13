package com.allens.dialog.annotation

import androidx.annotation.IntDef

/**
 *
 * @Description:
 * @author : jianghaiyang
 * @date: 2022/3/25 00:11
 * @version: 1.0.0
 */
@IntDef(ItemType.TOP, ItemType.BOTTOM, ItemType.NORMAL, ItemType.SINGLE)
internal annotation class ItemType {
    companion object {
        const val TOP = 1
        const val BOTTOM = 2
        const val NORMAL = 3
        const val SINGLE = 4
    }
}