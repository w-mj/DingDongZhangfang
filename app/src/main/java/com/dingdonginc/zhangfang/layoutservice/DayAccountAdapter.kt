package com.dingdonginc.zhangfang.layoutservice

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.dingdonginc.zhangfang.BR
import com.dingdonginc.zhangfang.R
import com.dingdonginc.zhangfang.databinding.ListviewItemBinding
import com.dingdonginc.zhangfang.models.Account
import com.dingdonginc.zhangfang.models.Check
import com.dingdonginc.zhangfang.viewmodels.AccountListViewModel

//Home page 每日账单列表 adapter
class DayAccountAdapter(var alvm: AccountListViewModel, var vid : Int,var list: ArrayList<Account>): RecyclerView.Adapter<DayAccountAdapter.MainViewHolder>() {
    //set viewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val viewDataBinding = DataBindingUtil.inflate<ListviewItemBinding>(LayoutInflater.from(parent.context), R.layout.listview_item, parent, false)
        return MainViewHolder(viewDataBinding)
    }

    override fun getItemCount() = list.size

    //binding each list item
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.viewDataBinding.setVariable(vid, list[position])
        holder.viewDataBinding.setVariable(BR.alvm, alvm)
        holder.viewDataBinding.executePendingBindings()
    }

    //viewHolder
    class MainViewHolder(viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        val viewDataBinding: ViewDataBinding = viewDataBinding
    }
}