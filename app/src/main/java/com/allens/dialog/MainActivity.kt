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
                radius = 40f
                canceledOnTouchOutside = true
                cancelable = true
                layout = R.layout.dialog_custom
                viewCreate = { view, dialog ->
                    view.findViewById<TextView>(R.id.text).text = "Allens"
                }
                widthScale = 0.5f
                heightScale = 0.5f
                alpha = 0.5f
                dimAmount = 0.7f
                target = ""
                back = {
                    Toast.makeText(this@MainActivity, "back", Toast.LENGTH_SHORT).show()
                }
                gravity = Gravity.CENTER
            }.show(this)
        }

        addClick("自定义Dialog 动态添加View") {
            CommonDialog().apply {
                radius = 40f
                contentView = TextView(this@MainActivity).apply {
                    text = "动态添加view"
                    setBackgroundColor(Color.RED)
                }
                height = 300
                width = 600
                target = ""
                gravity = Gravity.TOP
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