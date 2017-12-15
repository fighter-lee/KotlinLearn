package com.fighter_lee.kotlinlearn.learn

import android.util.Log
import kotlinx.coroutines.experimental.*
import org.jetbrains.anko.custom.async
import java.util.concurrent.TimeUnit
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

        /**
         * 协程通过将复杂性放入库来简化异步编程。程序的逻辑可以在协程中顺序地表达，而底层库会为我们解决其异步性。
         */
        async { 协程() }
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
        val filter = listOf.filter { it: Int ->
            return@filter it > 4
        }

        //或者加上标签
        listOf.filter lit@ {
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

    /**
     * 优雅的处理线程阻塞的问题
     *
     * https://blog.dreamtobe.cn/kotlin-coroutines/
     */
    fun 协程() {
//        val list = List(10000, {
//            launch(CommonPool, CoroutineStart.DEFAULT, {
//                Log.d(TAG, "协程: ${Thread.currentThread().name} start")
//                delay(2, TimeUnit.SECONDS)
//                Log.d(TAG, "协程: ${Thread.currentThread().name}")
//            })
//        })
////
//
//        val threadList = List(10000, {
//            thread {
//                Log.d(TAG, "协程: ${Thread.currentThread().name} start")
//                Thread.sleep(2000)
//                Log.d(TAG, "协程: ${Thread.currentThread().name}")
//            }
//        })
//
//        threadList.forEach { it.join() }


        /**
         * run(CoroutineContext) { ... }:
         * 创建一个运行在CoroutineContext制定线程中的区块，效果是运行在CoroutineContext线程中并且挂起父coroutine上下文直到区块执行完毕
         *  runBlocking(CoroutineContext) { ... }:
         *  创建一个coroutine并且阻塞当前线程直到区块执行完毕，这个一般是用于桥接一般的阻塞试编程方式到coroutine编程方式的，不应该在已经是coroutine的地方使用
         * launch(CoroutineContext) { ... }:
         * 创建运行在CoroutineContext中的coroutine，返回的Job支持取消、启动等操作，不会挂起父coroutine上下文；可以在非coroutine中调用
         * suspend fun methodName() { ... }:
         * 申明一个suspend方法，suspend方法中能够调用如delay这些coroutine特有的非阻塞方法；需要注意的是suspend方法只能在coroutine中执行
         * async(CoroutineContext) { ... }:
         * 创建运行在CoroutineContext中的coroutine，并且带回返回值(返回的是Deferred，我们可以通过await等方式获得返回值)
         */
        runBlocking {
            Log.d(TAG, "${Thread.currentThread().name} start")
            val launch = launch(CommonPool, CoroutineStart.LAZY, {
                Log.d(TAG, "线程启动")
                delay(2, TimeUnit.SECONDS)
                Log.d(TAG, "线程结束")
            })
            launch.join()
            Log.d(TAG, "${Thread.currentThread().name} end")
        }

        Log.d(TAG, "================================================")

//        async {
//            Log.d(TAG, "${Thread.currentThread().name} start")
//            val launch = launch(CommonPool, CoroutineStart.LAZY, {
//                Log.d(TAG, "线程启动")
//                delay(2, TimeUnit.SECONDS)
//                Log.d(TAG, "线程结束")
//            })
//            launch.start()
//            Log.d(TAG, "${Thread.currentThread().name} end")
//        }

//        Log.d(TAG, "================================================")

        runBlocking {
            val async = async(CommonPool, CoroutineStart.LAZY, {
                Log.d(TAG, "${Thread.currentThread().name} start")
                doDelay()
            })
            //async.await() 此时会挂起当前上下文，直到返回结果
            Log.d(TAG, "async result: ${async.await()}")
        }

        Log.d(TAG, "================================================")

        /**
         * 指定不同的线程
         *
         * Unconfined:
         * 执行coroutine是在调用者的线程，但是当在coroutine中第一个挂起之后，后面所在的线程将完全取决于调用挂起方法的线程(如delay一般是由kotlinx.coroutines.DefaultExecutor中的线程调用)
         *
         * CoroutineScope#coroutineContext(旧版本这个变量名为context):
         * 执行coroutine始终都是在coroutineContext所在线程(coroutineContext就是CoroutineScope的成员变量，因此就是CoroutineScope实例所在coroutine的线程)，
         *
         * CommonPool:
         * 执行coroutine始终都是在CommonPool(ForkJoinPool)线程池提供的线程中；使用CommonPool这个context可以有效使用CPU多核, CommonPool中的线程个数与CPU核数一样。
         *
         * newSingleThreadContext:
         * 执行coroutine始终都是在创建的单线程中
         *
         * newFixedThreadPoolContext:
         * 执行的coroutine始终都是在创建的fixed线程池中
         */

        runBlocking {
            val listUnconfined = List(5, {
                launch(Unconfined, CoroutineStart.LAZY, {
                    //此处线程和调用处线程一致
                    Log.d(TAG, "Unconfined: ${Thread.currentThread().name}")
                    doDelay()
                    //delay 一般由kotlinx.coroutines.ScheduledExecutor调用
                    Log.d(TAG, "Unconfined: ${Thread.currentThread().name}")
                })
            })
            listUnconfined.forEach { it.join() }
        }

        Log.d(TAG, "================================================")

        runBlocking {
            val list = List(5, {
                //此处的context为runBlocking的构造函数的参数，所以协程和父协程一致
                launch(context, CoroutineStart.LAZY, {
                    Log.d(TAG, "CoroutineContext: ${Thread.currentThread().name}")
                })
            })
            list.forEach { it.join() }
        }

        Log.d(TAG, "================================================")

        val thread1 = newSingleThreadContext("thread 1")
        val thread2 = newSingleThreadContext("thread 2")
        runBlocking(thread1) {
            Log.d(TAG, "1 thread name:${Thread.currentThread().name}")
            doDelay()
            kotlinx.coroutines.experimental.run(thread2){
                Log.d(TAG, "2 thread name:${Thread.currentThread().name}")
                doDelay()
            }

            Log.d(TAG, "3 thread name:${Thread.currentThread().name}")
        }

        Log.d(TAG, "协程: 结束")
    }

    suspend fun doDelay(): Int {
        delay(2, TimeUnit.SECONDS)
        return 2
    }


}