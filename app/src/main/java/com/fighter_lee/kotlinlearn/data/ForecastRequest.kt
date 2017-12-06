package com.fighter_lee.kotlinlearn.data

import com.google.gson.Gson

/**
 * @author fighter_lee
 * @date 2017/12/6
 */
class ForecastRequest() {
    companion object {
//        http://samples.openweathermap.org/data/2.5/forecast/daily?id=524901&lang=zh_cn&appid=b1b15e88fa797225412429c1c50c122a1
        private val APP_ID = "b1b15e88fa797225412429c1c50c122a1"
        private val URL = "http://samples.openweathermap.org/data/2.5/forecast/daily?id=524901&lang=zh_cn"
        private val COMPLETE_URL = "$URL&appid=$APP_ID"
    }

    fun execute():ForecastResult{
        val readText = java.net.URL(COMPLETE_URL).readText()
        return Gson().fromJson(readText,ForecastResult::class.java)
    }
}