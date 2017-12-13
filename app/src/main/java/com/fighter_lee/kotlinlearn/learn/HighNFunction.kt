package com.fighter_lee.kotlinlearn.learn

import android.util.Log
import java.util.concurrent.locks.ReentrantLock

/**
 * @author fighter_lee
 * @date 2017/12/12
 *
 * 高阶函数
 */
object HighNFunction {
    var TAG = "HighNFunction"
    open fun start() {
        val listOf = listOf<Int>(2, 3, 4, 5, 232, 5, 4, 3, 2, 6, 999)
//        max(listOf,less)

        内联函数()
        函数引用()

        val lock = ReentrantLock()
        Log.d(TAG, "start: ${lock(lock,::body)}")
    }

    fun <T> max(collection: Collection<T>, less: (T, T) -> Boolean): T? {
        var max: T? = null
        for (it in collection) {
            if (max == null || less(max, it))
                max = it
        }
        return max
    }

    private fun <T> less(o1: T, o2: T): Boolean {
        if (o1 is String && o2 is String) {
            return o1.length < o2.length
        }

        if (o1 is Int && o2 is Int) {
            return o1 < o2
        }
        return true
    }

    private fun 内联函数() {

    }

    private fun 函数引用() {

        fun isOdd(x:Int): Boolean {
            return x%2 !=0
        }

        fun isOdd(string: String):Boolean{
            return string.length %2 != 0
        }

        val listOf = listOf<Int>(1, 2, 3, 4)
        val stringList = listOf<String>("1", "22", "333", "4444")


        fun method(collection: Collection<Int>,method:(Int)->Boolean) {
            for (i in collection){
                if (method(i)){
                    Log.d(TAG, "method: $i")
                }
            }
        }

        method(listOf,::isOdd)

        /**
         * :: 可以用于重载函数
         *
         * 同时可以做为引用
         */
        Log.d(TAG, "函数引用: ${stringList.filter(::isOdd)}")

        val predicate: (String) -> Boolean = ::isOdd
        Log.d(TAG, "函数引用: ${stringList.filter(predicate)}")



    }

    fun <T> lock(lock: ReentrantLock, body: () -> T): T {
        lock.lock()
        try {
            return body()
        }
        finally {
            lock.unlock()
        }
    }

    fun body(): String {
        return "aaa"
    }

}