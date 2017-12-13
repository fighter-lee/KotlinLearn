package com.fighter_lee.kotlinlearn

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fighter_lee.kotlinlearn.data.ForecastResult
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author fighter_lee
 * @date 2017/12/5
 */
class MyRvAdapter(var list:List<ForecastResult.ListBean>,var mCx:Context): RecyclerView.Adapter<MyRvAdapter.MyViewHolder>() {

    companion object {
        var TAG = "MyRvAdapter"
    }


    private lateinit var callback: (item: View, posit: Int) -> Unit

    /**
     * TODO 回调 是否有更好的方法
     * http://www.jianshu.com/p/f711a1bc887a
     */
    open fun setOnclickListener(callback:(item:View, posit:Int)->Unit){
        this.callback = callback
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
//        Log.d(TAG,"data:${list[position].dt}")
        val data = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(list[position].dt * 1000))
//        Log.d(TAG,data)
        holder!!.date.text = data
        holder.description.text = list[position].weather?.get(0)?.description ?: "null"
        holder.maxTemperature.text = list[position].temp?.max.toString()
        holder.minTemperature.text = list[position].temp?.min.toString()
        holder.itemView.setOnClickListener(View.OnClickListener {
            callback.invoke(holder.itemView,position)
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val inflate = LayoutInflater.from(mCx).inflate(R.layout.item_forecast, parent, false)
        return MyViewHolder(inflate)
    }

    class MyViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
        var date:TextView = itemView.findViewById(R.id.date) as TextView
        var description:TextView = itemView.findViewById(R.id.description) as TextView
        var maxTemperature:TextView = itemView.findViewById(R.id.maxTemperature) as TextView
        var minTemperature:TextView = itemView.findViewById(R.id.minTemperature) as TextView
    }
}