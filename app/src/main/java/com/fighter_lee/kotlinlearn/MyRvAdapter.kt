package com.fighter_lee.kotlinlearn

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * @author fighter_lee
 * @date 2017/12/5
 */
class MyRvAdapter(var list:List<String>): RecyclerView.Adapter<MyRvAdapter.MyViewHolder>() {
    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        holder!!.tv.setText(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        return MyViewHolder(TextView(parent!!.context))
    }

    class MyViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
        var tv:TextView = itemView as TextView;
    }
}