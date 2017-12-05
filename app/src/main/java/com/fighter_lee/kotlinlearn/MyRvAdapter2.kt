package com.fighter_lee.kotlinlearn

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * @author fighter_lee
 * @date 2017/12/5
 */
class MyRvAdapter2 : RecyclerView.Adapter<MyRvAdapter2.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder? {
        return null
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 0
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
