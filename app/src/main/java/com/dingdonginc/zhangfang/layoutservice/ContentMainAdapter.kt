package com.dingdonginc.zhangfang.layoutservice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.dingdonginc.zhangfang.R
import com.dingdonginc.zhangfang.databinding.DayAccountsBinding
import com.dingdonginc.zhangfang.models.DayAccounts

//Home Page 全部账单列表 adapter
class ContentMainAdapter(var vid : Int,var list: ArrayList<DayAccounts>): RecyclerView.Adapter<ContentMainAdapter.MainViewHolder>() {
    //set viewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val viewDataBinding = DataBindingUtil.inflate<DayAccountsBinding>(LayoutInflater.from(parent.context), R.layout.day_accounts, parent, false)
        return MainViewHolder(viewDataBinding)
    }

    override fun getItemCount() = list.size

    //set each list item
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.viewDataBinding.setVariable(vid, list[position])
        holder.viewDataBinding.executePendingBindings()
    }
    //viewHolder
    class MainViewHolder(viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        val viewDataBinding: ViewDataBinding = viewDataBinding
    }
}