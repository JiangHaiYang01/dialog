package com.allens.dialog.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.Color.parseColor
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import com.allens.dialog.R
import com.allens.dialog.annotation.DialogType
import com.allens.dialog.annotation.ItemType
import com.allens.dialog.util.dp2px
import com.allens.dialog.util.radius

/**
 * @author jhy
 * @description 底部弹出窗的样式
 * @date 2021/10/12
 */
class SweetDialog : BaseDialog() {

    companion object {
        private const val LINEAR_ID = 0x00
    }

    private var mItems: MutableList<Wrapper> = mutableListOf()

    private lateinit var mLinearLayout: LinearLayout

    /* 默认的Item tv 字体颜色 **/
    var itemTextColor = parseColor("#888888")

    /*  默认的Item 背景颜色 **/
    var itemBackColor = parseColor("#FFFFFF")

    /* 默认的Item 字体大小 **/
    var itemTextSize = 14f

    /* 默认的Item 字体高度 **/
    var itemTextHeight = 45

    /* 默认的Item 圆角 **/
    var itemRadius = 25f

    /* 是否显示分割线 **/
    var showLine: Boolean = true

    /* item 分割线自定义 **/
    var line: ((Context?) -> View)? = null

    /* 默认的margin top **/
    var marginTop: Int = 40

    /* 默认的margin bottom **/
    var marginBottom: Int = 40

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val scrollView = ScrollView(context)
        val linearLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            id = LINEAR_ID
        }
        scrollView.addView(linearLayout)
        return scrollView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSweetDefault()
        mLinearLayout = view.findViewById(LINEAR_ID)
        addItemView()
    }

    private fun createDefaultLine(): View {
        return View(context).apply {
            setBackgroundColor(Color.GRAY)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                1
            )
        }
    }

    /**
     * 设置默认的属性
     */
    private fun setSweetDefault() {
        gravity = Gravity.BOTTOM
        widthScale = 0.8f
        setWindowAnimations(R.style.SheetDialogAnimation)
    }


    fun addCancel(
        info: String,
        textSize: Float = itemTextSize,
        color: Int = itemTextColor,
        backgroundColor: Int = itemBackColor,
        height: Int = itemTextHeight,
        longClick: (() -> Unit)? = null,
        dismiss: Boolean = true,
        theme: ((TextView) -> Unit)? = null,
        singleClick: (() -> Unit)? = null,
        view: ((Context?) -> View)? = null,
    ) = apply {
        addItem(
            info, textSize, color, backgroundColor, height,
            longClick, dismiss, theme, singleClick, view,
            DialogType.CANCEL
        )
    }

    fun addTitle(
        info: String,
        textSize: Float = itemTextSize,
        color: Int = itemTextColor,
        backgroundColor: Int = itemBackColor,
        height: Int = itemTextHeight,
        longClick: (() -> Unit)? = null,
        dismiss: Boolean = true,
        theme: ((TextView) -> Unit)? = null,
        singleClick: (() -> Unit)? = null,
        view: ((Context?) -> View)? = null,
    ) = apply {
        addItem(
            info, textSize, color, backgroundColor, height,
            longClick, dismiss, theme, singleClick, view,
            DialogType.TITLE
        )
    }

    fun addItem(
        info: String,
        textSize: Float = itemTextSize,
        color: Int = itemTextColor,
        backgroundColor: Int = itemBackColor,
        height: Int = itemTextHeight,
        longClick: (() -> Unit)? = null,
        dismiss: Boolean = true,
        theme: ((TextView) -> Unit)? = null,
        singleClick: (() -> Unit)? = null,
        view: ((Context?) -> View)? = null,
    ) = apply {
        addItem(
            info, textSize, color, backgroundColor, height,
            longClick, dismiss, theme, singleClick, view,
            DialogType.ITEM
        )
    }

    /**
     * 添加一个TextView作为Item
     *
     * [info]               名称
     * [textSize]           字体大小 sp
     * [color]              字体颜色
     * [backgroundColor]    背景颜色
     * [height]             item高度 dp
     * [dismiss]            点击是否消失
     * [singleClick]        点击事件
     * [longClick]          长按事件
     * [theme]              TextView的属性
     * [view]               自定义View
     * [type]               类型
     */
    private fun addItem(
        info: String,
        textSize: Float = itemTextSize,
        color: Int = itemTextColor,
        backgroundColor: Int = itemBackColor,
        height: Int = itemTextHeight,
        longClick: (() -> Unit)? = null,
        dismiss: Boolean = true,
        theme: ((TextView) -> Unit)? = null,
        singleClick: (() -> Unit)? = null,
        view: ((Context?) -> View)? = null,
        type: Int
    ) = apply {
        addWrapper(
            Wrapper(
                info = info, textSize = textSize,
                color = color, dismiss = dismiss,
                backgroundColor = backgroundColor, singleClickListener = singleClick,
                longClickListener = longClick, type = type, view = view,
                height = height.dp2px(), theme = theme
            )
        )
    }

    private fun addWrapper(
        wrapper: Wrapper,
        @DialogType type: Int = DialogType.ITEM
    ) {
        when (type) {
            DialogType.TITLE -> mItems.add(0, wrapper)
            DialogType.ITEM -> {
                val size = mItems.size
                val insertIndex = if (size == 0) {
                    0
                } else {
                    size - 1
                }
                mItems.add(insertIndex, wrapper)
            }
            DialogType.CANCEL -> mItems.add(wrapper)
        }
    }

    private fun addItemView() {
        // 将Cancel 过滤掉
        val filter = mItems.filter { it.type != DialogType.CANCEL }
        val size = filter.size
        if (size == 1) {
            mLinearLayout.addView(createView(mItems[0], ItemType.SINGLE))
            return
        }
        filter.sortedBy { it.type != DialogType.TITLE }
            .forEachIndexed { index, wrapper ->
                val view = when (index) {
                    0 -> createView(wrapper, ItemType.TOP)
                    size - 1 -> createView(wrapper, ItemType.BOTTOM)
                    else -> createView(wrapper, ItemType.NORMAL)
                }
                if (index != 0 && index < size && showLine) {
                    mLinearLayout.addView(line?.invoke(context) ?: createDefaultLine())
                }
                mLinearLayout.addView(view)
            }

        // 添加Cancel
        mItems.filter { it.type == DialogType.CANCEL }.forEach {
            mLinearLayout.addView(
                createItemTextView(
                    wrapper = it,
                    type = ItemType.SINGLE,
                    marginTop = marginTop,
                    marginBottom = marginBottom,
                )
            )
        }
    }

    private fun createView(
        wrapper: Wrapper,
        @ItemType type: Int = ItemType.NORMAL
    ): View =
        wrapper.view?.invoke(context) ?: createItemTextView(
            wrapper = wrapper,
            type = type
        )

    private fun createItemTextView(
        wrapper: Wrapper,
        @ItemType type: Int = ItemType.NORMAL,
        marginTop: Int = 0,
        marginBottom: Int = 0
    ): TextView {
        return TextView(context).apply {
            this.text = wrapper.info
            this.gravity = Gravity.CENTER
            wrapper.textSize?.let { this.textSize = it }
            wrapper.height?.let { this.height = it }
            wrapper.color?.let { setTextColor(it) }
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            ).apply {
                gravity = Gravity.CENTER
                setMargins(0, marginTop, 0, marginBottom)
            }
            this.setOnClickListener {
                if (wrapper.dismiss) {
                    dialog?.dismiss()
                }
                wrapper.singleClickListener?.invoke()
            }
            this.setOnLongClickListener {
                wrapper.longClickListener?.invoke()
                false
            }
            when (type) {
                ItemType.NORMAL -> this.radius(color = wrapper.backgroundColor)
                ItemType.TOP -> this.radius(
                    tl = itemRadius,
                    tr = itemRadius,
                    color = wrapper.backgroundColor
                )
                ItemType.SINGLE -> this.radius(radius = itemRadius, color = wrapper.backgroundColor)
                ItemType.BOTTOM -> this.radius(
                    bl = itemRadius,
                    br = itemRadius,
                    color = wrapper.backgroundColor
                )
            }
            // 自定义属性
            wrapper.theme?.invoke(this)
        }
    }
}