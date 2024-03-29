package com.dingdonginc.zhangfang.layoutservice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.dingdonginc.zhangfang.R
import com.dingdonginc.zhangfang.databinding.TypelistItemBinding
import com.dingdonginc.zhangfang.models.Account

class TypesAdapter(var vid : Int,var list: ArrayList<Account>): RecyclerView.Adapter<TypesAdapter.MainViewHolder>() {
    //set viewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val viewDataBinding = DataBindingUtil.inflate<TypelistItemBinding>(LayoutInflater.from(parent.context), R.layout.typelist_item, parent, false)
        return MainViewHolder(viewDataBinding)
    }

    override fun getItemCount() = list.size

    //binding each list item
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.viewDataBinding.setVariable(vid, list[position])
        holder.viewDataBinding.executePendingBindings()
    }

    //viewHolder
    class MainViewHolder(viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        val viewDataBinding: ViewDataBinding = viewDataBinding
    }
}