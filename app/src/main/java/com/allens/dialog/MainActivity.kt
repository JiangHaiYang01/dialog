package com.allens.dialog

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.allens.dialog.dialog.CommonDialog
import com.allens.dialog.dialog.SweetDialog


class MainActivity : SampleAct() {
    override fun onCreate() {
        addClick("自定义Dialog xml 布局") {
            CommonDialog().apply {
                /* 设置Dialog圆角 **/
                radius = 40f
                /* 点击屏幕dialog不消失 **/
                canceledOnTouchOutside = true
                /* 点击物理返回键dialog不消失 **/
                cancelable = true
                /* 布局ID **/
                layout = R.layout.dialog_custom
                /* 寻找布局 **/
                viewCreate = { view, dialog ->
                    view.findViewById<TextView>(R.id.text).text = "Allens"
                }
                /* 宽度比例 **/
                widthScale = 0.5f
                /* 高度比例 **/
                heightScale = 0.5f
                /* 设置透明度 **/
                alpha = 0.5f
                /* 设置黑暗度（Dialog 窗口背景的黑暗度） **/
                dimAmount = 0.7f
                /* target **/
                target = ""
                /* 返回 **/
                back = {
                    Toast.makeText(this@MainActivity, "back", Toast.LENGTH_SHORT).show()
                }
                /* 位置 **/
                gravity = Gravity.CENTER
            }.show(this)
        }

        addClick("自定义Dialog 动态添加View") {
            CommonDialog().apply {
                radius = 40f
                /* 动态添加View **/
                contentView = TextView(this@MainActivity).apply {
                    text = "动态添加view"
                    setBackgroundColor(Color.RED)
                }
                /* 高度 **/
                height = 300
                /* 宽度 **/
                width = 600
            }.show(this)
        }

        addClick("选择框") {
            SweetDialog().apply {
                /* 默认的Item tv 字体颜色 **/
                itemTextColor = Color.parseColor("#888888")
                /*  默认的Item 背景颜色 **/
                itemBackColor = Color.parseColor("#FFFFFF")
                /*  默认的Item 背景颜色 **/
                itemBackColor = Color.parseColor("#FFFFFF")
                /* 默认的Item 字体大小 **/
                itemTextSize = 14f
                /* 默认的Item 字体高度 **/
                itemTextHeight = 45
                /* 默认的Item 圆角 **/
                itemRadius = 25f
                /* 是否显示分割线 **/
                showLine = true
                /* 宽度比例 **/
                widthScale = 0.8f
                /* Item 分割线自定义 **/
                line = { context ->
                    View(context).apply {
                        setBackgroundColor(Color.RED)
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            1
                        )
                    }
                }
                /* 默认的margin top **/
                marginTop = 40
                /* 默认的margin bottom **/
                marginBottom = 40

                addTitle("请选择")
                addItem("默认选项",
                    // 字体大小
                    textSize = 20f,
                    // 字体颜色
                    color = Color.GREEN,
                    // 背景颜色
                    backgroundColor = Color.YELLOW,
                    // 高度
                    height = 80,
                    longClick = {
                        toast("item1 long click")
                    },
                    singleClick = {
                        toast("item1 click")
                    })
                addItem("item2",
                    // 自定义view
                    view = { context ->
                        TextView(context).apply {
                            text = "自定义view"
                            setBackgroundColor(Color.BLUE)
                            layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                100
                            )
                        }
                    })
                addItem(
                    "item3",
                    singleClick = {
                        toast("点击不消失")
                    },
                    dismiss = false
                )
                addItem("自定义TextView样式",
                theme = {
                    it.textSize = 20f
                })
                addCancel("取消", color = Color.RED)
            }.show(this)
        }
    }
}