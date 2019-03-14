package com.fighter_lee.kotlinlearn

import android.util.Log
import com.fighter_lee.kotlinlearn.data.Bean

/**
 * Created by fighter_lee on 2017/8/2.
 *
 * 主要为let/with/run/apply/also
 * https://blog.csdn.net/u013064109/article/details/78786646
 */
class Test1 {

    companion object {
        val TAG = "Test1"
    }

    fun main(args: Array<String>) {
        print("main")
    }

    fun letTest() {
        var sss = "test"
        val s = sss?.let {
            Log.d(TAG, it)
            1000
        }
        Log.d(TAG, "result :${s}")
    }

    fun withTest() {
        var bean = Bean()
        bean.let {
            it.age = 18
            it.name = "Jack"
        }
        val result = with(bean) {
            Log.d(TAG, "withTest: name:$name , age:$age")
            1000
        }
        Log.d(TAG, "withTest: $result")
    }

    /**
     *  适用于let,with函数任何场景。
     *  因为run函数是let,with两个函数结合体，准确来说它弥补了let函数在函数体内必须使用it参数替代对象，在run函数中可以像with函数一样可以省略，直接访问实例的公有属性和方法，
     *  另一方面它弥补了with函数传入对象判空问题，在run函数中可以像let函数一样做判空处理
     */
    fun runTest() {
        var bean = Bean()
        val result = bean?.run {
            name = "Jack"
            age = 18
            Log.d(TAG, "runTest: name:$name , age:$age")
            1000
        }
        Log.d(TAG, "runTest: $result")
    }

    /**
     * 整体作用功能和run函数很像，唯一不同点就是它返回的值是对象本身，
     * 而run函数是一个闭包形式返回，返回的是最后一行的值。
     *
     * apply实现多层判断很方便
     */
    fun applyTest() {
        val bean = Bean()?.apply {
            name = "Jack"
            age = 18
        }
        Log.d(TAG, "applyTest: ${bean.name}  ${bean.age}")

        bean.apply {

        }.grade?.apply {

        }?.english?.apply {

        }?.score?.apply {
            Log.d(TAG, "applyTest: $this")
        }
    }

    /**
     * also函数的结构实际上和let很像唯一的区别就是返回值的不一样，let是以闭包的形式返回，返回函数体内最后一行的值
     * 而also函数返回的则是传入对象的本身
     */
    fun alsoTest() {
        val bean = Bean().also {
            it.name = "Jack"
        }.also {
            it.age = 18
        }
        Log.d(TAG, "alsoTest: $bean")
    }
}