package com.fighter_lee.kotlinlearn

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.fighter_lee.kotlinlearn.data.ForecastRequest
import com.fighter_lee.kotlinlearn.data.ForecastResult
import com.fighter_lee.kotlinlearn.learn.HighNFunction
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

//        Basics.method()
//        Basics.range()
//        Basics.nullSafe()

        HighNFunction.start()

    }

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

}
