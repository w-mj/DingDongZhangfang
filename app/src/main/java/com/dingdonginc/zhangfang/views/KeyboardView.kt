package com.dingdonginc.zhangfang.views


import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.util.AttributeSet
import android.util.Log
import com.dingdonginc.zhangfang.R

import java.util.Arrays

/**
 * Created by Administrator on 2018/3/25.
 */

class PwdKeyboardView : KeyboardView, KeyboardView.OnKeyboardActionListener {

    private val delKeyBackgroundColor = -0x333334

    private var keyIconRect: Rect? = null

    private var listener: OnKeyListener? = null


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        Log.d(TAG, "PwdKeyboardView: two params")
        init(context)

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        Log.d(TAG, "PwdKeyboardView: three params")
        init(context)
    }


    private fun init(context: Context) {
        val keyboard = Keyboard(context, R.xml.digital_keyboard)
        setKeyboard(keyboard)
        isEnabled = true
        isFocusable = true
        isPreviewEnabled = false  // 设置点击按键不显示预览气泡
        onKeyboardActionListener = this
    }

    /**
     * 重新绘制删除按键和空白键
     *
     * @param canvas
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val keys = keyboard.keys
        for (key in keys) {
            if (key.codes[0] == KEY_EMPTY) {
                drawKeyBackground(key, canvas, delKeyBackgroundColor)
            }
            if (key.codes[0] == Keyboard.KEYCODE_DELETE) {
                drawKeyBackground(key, canvas, delKeyBackgroundColor)
                drawKeyIcon(key, canvas, resources.getDrawable(R.drawable.ic_delete))
            }
        }

    }

    /**
     * 绘制按键的背景
     *
     * @param key
     * @param canvas
     * @param color
     */
    private fun drawKeyBackground(key: Keyboard.Key, canvas: Canvas, color: Int) {
        val drawable = ColorDrawable(color)
        drawable.setBounds(key.x, key.y, key.x + key.width, key.y + key.height)
        drawable.draw(canvas)
    }

    /**
     * 绘制按键的 icon
     *
     * @param key
     * @param canvas
     * @param iconDrawable
     */
    private fun drawKeyIcon(key: Keyboard.Key, canvas: Canvas, iconDrawable: Drawable?) {
        if (iconDrawable == null) {
            return
        }
        // 计算按键icon 的rect 范围
        if (keyIconRect == null || keyIconRect!!.isEmpty) {
            // 得到 keyicon 的显示大小，因为图片放在不同的drawable-dpi目录下，显示大小也不一样
            val intrinsicWidth = iconDrawable.intrinsicWidth
            val intrinsicHeight = iconDrawable.intrinsicHeight
            var drawWidth = intrinsicWidth
            var drawHeight = intrinsicHeight
            // 限制图片的大小，防止图片按键范围
            if (drawWidth > key.width) {
                drawWidth = key.width
                // 此时高就按照比例缩放
                drawHeight = (drawWidth * 1.0f / intrinsicWidth * intrinsicHeight).toInt()
            } else if (drawHeight > key.height) {
                drawHeight = key.height
                drawWidth = (drawHeight * 1.0f / intrinsicHeight * intrinsicWidth).toInt()
            }
            // 获取图片的 x,y 坐标,图片在按键的正中间
            val left = key.x + key.width / 2 - drawWidth / 2
            val top = key.y + key.height / 2 - drawHeight / 2
            keyIconRect = Rect(left, top, left + drawWidth, top + drawHeight)
        }

        if (keyIconRect != null && !keyIconRect!!.isEmpty) {
            iconDrawable.bounds = keyIconRect!!
            iconDrawable.draw(canvas)
        }
    }


    override fun onPress(primaryCode: Int) {

    }

    override fun onRelease(primaryCode: Int) {

    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray) {
        Log.d(TAG, "onKey: primaryCode = " + primaryCode + ", keyCodes = " + Arrays.toString(keyCodes))
        if (primaryCode == KEY_EMPTY) {
            return
        }
        if (listener != null) {
            if (primaryCode == Keyboard.KEYCODE_DELETE) {
                listener!!.onDelete()
            } else {
                listener!!.onInput(primaryCode.toChar().toString())
            }
        }


    }

    override fun onText(charSequence: CharSequence) {

    }

    override fun swipeLeft() {

    }

    override fun swipeRight() {

    }

    override fun swipeDown() {

    }

    override fun swipeUp() {

    }

    interface OnKeyListener {
        fun onInput(text: String)

        fun onDelete()
    }

    fun setOnKeyListener(listener: OnKeyListener) {
        this.listener = listener
    }

    companion object {

        private val TAG = "PwdKeyboardView"


        private val KEY_EMPTY = -10
    }


}
