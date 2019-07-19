package com.dingdonginc.zhangfang


import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.dingdonginc.zhangfang.models.Check

class ListViewAdapter(var context: Context,var list: ArrayList<Check>):BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        var vH:ViewHolder?=null
        var view:View?=null
//        if(convertView==null){
//            vH = ViewHolder()
//            view = View.inflate(context,R.layout.listview_item,null);
//            vH.textView=view.findViewById(R.id.title)
//            view.tag=vH
//        }else{
//            view=convertView
//            vH=view.tag as ViewHolder
//        }
//        vH.textView?.text=list.get(position).title
        return view!!
    }
    inner class ViewHolder{
        var textView:TextView?=null
    }

    override fun getItem(position: Int): Any {
        return list.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }
}
