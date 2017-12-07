package com.fighter_lee.kotlinlearn

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.fighter_lee.kotlinlearn.data.ForecastRequest
import com.fighter_lee.kotlinlearn.data.ForecastResult
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    var TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv.setLayoutManager(LinearLayoutManager(this))

        rv.adapter = MyRvAdapter(dataLists,this)
        toast("max is ${max(11, 12)}")

        var b: String? = "xxx"
        b = null

        /**
         * 非空断言运算符（!!）将任何值转换为非空类型，若该值为空则抛出异常
         */
//        var l=b!!.length

        /**
         * Elvis 操作符表达，写作 ?:
         * 相当于 if-表达式 if（b != null）b else "null"
         */
        Log.d(TAG, b ?: "null")

        /**
         * TODO 问题 as? 为什么不能转换
         */
        var a: Int? = 1.2f as? Int
//        if (a != null) a as String else "null"
        Log.d(TAG, a?.toString() ?: "null")

        /**
         * for 循环
         * val mutableListOf = mutableListOf<String>()
         */
        val listOf = listOf<String>("a", "b", "c")
        for (s in listOf) {
            Log.d(TAG, s)
        }

        for (index in listOf.indices) {
            Log.d(TAG, "index:${index} is ${listOf[index]}")
        }

        /**
         * when 操作符
         * 类似于while
         */
        Log.d(TAG, "${whenFun()}  ${whenFun(1)}  ${whenFun(10)}  ${whenFun(5)}")

        /**
         * 使用区间
         */
        Log.d(TAG, "1 in 1..2 ${1 in 1..2}")
        Log.d(TAG, "1 in list ${1 in listOf.indices}")

        /**
         * 映射对象到变量中
         */
        val pair1 = Pair<Int, String>(1, "一")
        val pair2 = Pair<Int, String>(2, "二")
        val pair3 = Pair<Int, String>(3, "三")
        val mutableMapOf = mutableMapOf<Int, String>(pair1, pair2, pair3)
        for ((key, value) in mutableMapOf) {
            Log.d(TAG, "key:$key value:$value")
        }

//        Bb().setListener(Bb.BBB {  position: Int, ttt: Int ->
//
//        })
        (rv.adapter as MyRvAdapter).setOnclickListener({item, posit ->
            toast("position:$posit")
        })

        async {
            var forecastBean = ForecastRequest().execute()
            val list = forecastBean.list
            for (index in list!!.indices) {
                Log.d(TAG, list[index].temp.toString())
                dataLists.addAll(list)
            }
            uiThread {
                rv.adapter.notifyDataSetChanged();
            }
        }

    }

//    fun toast(message:String,length:Int= Toast.LENGTH_SHORT){
//        Toast.makeText(this,message,length).show()
//    }

    fun Context.toast(message: String, len: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, len).show()
    }

    fun max(a: Int, b: Int) =
            if (a > b) a else b

    private val dataLists = mutableListOf<ForecastResult.ListBean>()

    private val items = listOf(
            "Mon 6/23 - Sunny - 31/17",
            "Tue 6/24 - Foggy - 21/8",
            "Wed 6/25 - Cloudy - 22/17",
            "Thurs 6/26 - Rainy - 18/11",
            "Fri 6/27 - Foggy - 21/10",
            "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
            "Sun 6/29 - Sunny - 20/7"
    )

    fun whenFun(x: Int = 0): String {
        when (x) {
            0 -> return "is a"
            1, 2 -> return "1 or 2"
            in 10..100 -> return "10-100"
            else -> return "other"
        }
    }

}
