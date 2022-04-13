package com.allens.dialog.annotation

import androidx.annotation.IntDef

/**
 *
 * @Description:
 * @author : jianghaiyang
 * @date: 2022/3/13 11:50 下午
 * @version: 1.0.0
 */

@IntDef(SizeFrom.SCALE, SizeFrom.SPECIFY)
internal annotation class SizeFrom {
    companion object {

        /**
         * 比例
         */
        const val SCALE = 1

        /**
         * 指定大小
         */
        const val SPECIFY = 2
    }
}