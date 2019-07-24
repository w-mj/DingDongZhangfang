package com.dingdonginc.zhangfang.imageradio

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import android.widget.RemoteViews
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.*


@InverseBindingMethods(
    InverseBindingMethod(
        type=ImageRadio::class,
        attribute="app:selected1",
        event="selected1AttrChanged",
        method="getSelected"
    )
)
class ImageRadio : RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

//    val selected: ObservableField<Int>? = null
    private var onSelectedChangeListener: (()->Unit)? = null
    var selected = 0
        set(value) {
            field = value
            onSelectedChangeListener?.invoke()
        }

    init {
        ImageRadioService.view = WeakReference(this)
//        selected?.addOnPropertyChangedCallback(OnPropertyChange())
    }

//    private inner class OnPropertyChange : Observable.OnPropertyChangedCallback() {
//        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
//            if (selected.get() != null)
//                ImageRadioService.click(selected.get()!!)
//            else
//                Log.d("ImageRadio", "selected is null")
//        }
//
//    }


    companion object {
        @JvmStatic
        @BindingAdapter("android:background")
        fun setImageRadioItemBackground(view: ImageView, boundary: Boolean) {
            if (boundary)
                view.setBackgroundColor(0xFFFFC107.toInt())
            else
                view.setBackgroundColor(0x00FFC107)
        }

        @JvmStatic
        @BindingAdapter("selected1")
        fun setSelectItem(view: ImageRadio, selected: Int) {
            ImageRadioService.click(selected)
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "selected1")
        fun getSelected(view: ImageRadio): Int {
            return view.selected
        }

        @JvmStatic
        @BindingAdapter("selected1AttrChanged")
        fun setListener(radio: View?, selectedAttrChanged: InverseBindingListener?) {
            val img = radio as ImageRadio
            img.onSelectedChangeListener = {selectedAttrChanged!!.onChange()}
        }
    }

}

