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

        内联函数()
        函数引用()
        lambda()
        returnFun()
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

        val lock = ReentrantLock()
        Log.d(TAG, "start: ${lock(lock, ::body)}")

    }

    private fun lambda() {
        /**
         * 一个函数作为第二个参数。 其第二个参数是一个表达式
         */
        val max1 = max(listOf<String>("1", "22", "333", "4444"), less = { a, b -> a.length < b.length })

        var less1: (a: String, b: String) -> Boolean = { a, b -> a.length < b.length }
        var less2 = { a: String, b: String -> a.length < b.length }
        val max2 = max(listOf<String>("1", "22", "333", "4444"), less = less1)
        val max3 = max(listOf<String>("1", "22", "333", "4444"), less = less2)
        Log.d(TAG, "lambda: max1:$max1 , max2:$max2 , max2:$max3")

        val filter = listOf<String>("1", "22", "333", "4444")
                .filter { a: String -> a.toInt() > 100 }
        for (it in filter) {
            Log.d(TAG, "lambda: $it")
        }
    }

    /**
     * 局部返回
     *
     * 非局部的返回只支持传给内联函数的 lambda 表达式
     */
    private fun returnFun(): Int {
        val listOf = listOf<Int>(1, 2, 3, 4, 5, 6)

        //一个 lambda 表达式只有一个参数是很常见的。
        // 如果 Kotlin 可以自己计算出签名，它允许我们不声明唯一的参数，并且将隐含地为我们声明其名称为 it
        val filter = listOf.filter {
            it:Int->
            return@filter it > 4
        }

        //或者加上标签
        listOf.filter lit@{
            return@lit it > 4
        }

        filter.forEach {
            Log.d(TAG, "returnFun: $it")
        }
        return 1

    }

    private fun 函数引用() {

        fun isOdd(x: Int): Boolean {
            return x % 2 != 0
        }

        fun isOdd(string: String): Boolean {
            return string.length % 2 != 0
        }

        val listOf = listOf<Int>(1, 2, 3, 4)
        val stringList = listOf<String>("1", "22", "333", "4444")


        fun method(collection: Collection<Int>, method: (Int) -> Boolean) {
            for (i in collection) {
                if (method(i)) {
                    Log.d(TAG, "method: $i")
                }
            }
        }

        method(listOf, ::isOdd)

        /**
         * :: 可以用于重载函数
         *
         * 同时可以做为引用
         */
        Log.d(TAG, "函数引用: ${stringList.filter(::isOdd)}")

        val predicate: (String) -> Boolean = ::isOdd
        Log.d(TAG, "函数引用: ${stringList.filter(predicate)}")
    }

    /**
     * 使用高阶函数会带来一些运行时的效率损失：
     * 每一个函数都是一个对象，并且会捕获一个闭包。
     * 即那些在函数体内会访问到的变量。
     * 内存分配（对于函数对象和类）和虚拟调用会引入运行时间开销。
     *
     * inline
     * 通过内联化 lambda 表达式可以消除这类的开销
     *
     * noinline
     * 禁止内联
     */
    inline fun <T> lock(lock: ReentrantLock, noinline body: () -> T): T {
        lock.lock()
        try {
            return body()
        } finally {
            lock.unlock()
        }

    }

    fun body(): String {
        return "aaa"
    }

}