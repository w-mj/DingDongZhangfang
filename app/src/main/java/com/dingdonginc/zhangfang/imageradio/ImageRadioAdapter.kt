package com.dingdonginc.zhangfang.imageradio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.dingdonginc.zhangfang.BR
import com.dingdonginc.zhangfang.R

class ImageRadioAdapter(private val vms: List<ImageRadioItemViewModel>):
    RecyclerView.Adapter<ImageRadioAdapter.Holder>() {
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
            binding.setVariable(BR.onclick, OnClick(pos))
            binding.executePendingBindings()
        }
    }

    inner class OnClick(private val it: Int): View.OnClickListener {
        override fun onClick(p0: View?) {
            for (vm in vms)
                vm.clicked.set(false)
            vms[it].clicked.set(true)
        }
    }
}