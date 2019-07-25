package com.dingdonginc.zhangfang.imageradio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.dingdonginc.zhangfang.BR
import com.dingdonginc.zhangfang.R
import java.lang.ref.WeakReference

class ImageRadioAdapter(private val vms: List<ImageRadioItemViewModel>):
    RecyclerView.Adapter<ImageRadioAdapter.Holder>() {
    init {
        ImageRadioService.vms = WeakReference(vms)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.image_radio, parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int = vms.count()

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    inner class Holder(private val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            binding.setVariable(BR.vm, vms[pos])
            binding.setVariable(BR.pos, pos)
            binding.executePendingBindings()
        }
    }

}