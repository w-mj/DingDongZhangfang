package com.dingdonginc.zhangfang.layoutservice

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.dingdonginc.zhangfang.models.Wallet
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import com.dingdonginc.zhangfang.BR
import com.dingdonginc.zhangfang.R
import org.kodein.di.bindings.newScopeRegistry


class WalletListAdapter(private val list: ObservableList<Wallet>): BaseAdapter() {
    private inner class callback: ObservableList.OnListChangedCallback<ObservableList<Wallet>>() {
        override fun onChanged(sender: ObservableList<Wallet>?) {
            this@WalletListAdapter.notifyDataSetChanged()
        }

        override fun onItemRangeRemoved(sender: ObservableList<Wallet>?, positionStart: Int, itemCount: Int) {}

        override fun onItemRangeMoved(
            sender: ObservableList<Wallet>?,
            fromPosition: Int,
            toPosition: Int,
            itemCount: Int
        ) {}

        override fun onItemRangeInserted(sender: ObservableList<Wallet>?, positionStart: Int, itemCount: Int) {}

        override fun onItemRangeChanged(sender: ObservableList<Wallet>?, positionStart: Int, itemCount: Int) {}
    }


    init {
        // list.addOnListChangedCallback(callback())
    }
    override fun getView(p0: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ViewDataBinding? = if (convertView == null) {
            DataBindingUtil.inflate(
                LayoutInflater.from(parent!!.context),
                R.layout.wallet_list_template,
                parent,
                false)
        } else {
            DataBindingUtil.getBinding(convertView)
        }
        binding!!.setVariable(BR.wallet, list[p0])
        return binding.root
    }

    override fun getItem(p0: Int): Any = list[p0]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getCount(): Int = list.count()
}