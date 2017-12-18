package com.fighter_lee.kotlinlearn.learn

import android.util.Log
import kotlinx.coroutines.experimental.*
import java.util.concurrent.TimeUnit

/**
 * @author fighter_lee
 * @date 2017/12/18
 *
 * data类自动生成componentN()函数,按声明顺序对应于所有属性
 *
 */

data class 解构(var result: Int, var status: Status,var name: String) {

    constructor(result: Int,status: Status) : this(result,status,"two")

    constructor():this(-1,Status.Status1)

    companion object {
        val TAG = "解构"
    }

    enum class Status {
        Status1, Status2
    }

    init {
        Log.d(TAG, "init:$name")
    }

    fun doFun(): 解构 {
        val runBlocking = runBlocking {
            val async = async(CommonPool, CoroutineStart.LAZY, {
                //模拟网络请求
                delay(5, TimeUnit.SECONDS)
                return@async 1
            })
            if (1 == async.await()) {
                return@runBlocking 解构(result = 1, status = Status.Status1)
            } else {
                return@runBlocking 解构(result = 2, status = Status.Status2)
            }
        }
        return runBlocking
    }


}