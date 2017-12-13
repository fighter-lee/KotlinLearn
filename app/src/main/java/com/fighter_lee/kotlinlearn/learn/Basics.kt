package com.fighter_lee.kotlinlearn.learn

import android.util.Log

/**
 * @author fighter_lee
 * @date 2017/12/12
 */
object Basics {

    val TAG = "basics"

    open fun method() {

        /**
         * when 操作符
         * 类似于while
         *
         * whenFun(x = 1) 为命名函数
         */
        Log.d(TAG, "${whenFun()}  ${whenFun(x = 1)}  ${whenFun(10)}  ${whenFun(5)}")

        //命名函数
        methodTest(2,y = 4)

        vararg(1,2,3,"si",5)

        局部函数()

    }

    open fun list() {
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

        mutableMapOf
    }

    private fun whenFun(x: Int = 0): String {
        when (x) {
            0 -> return "is a"
            1, 2 -> return "1 or 2"
            in 10..100 -> return "10-100"
            else -> return "other"
        }
    }

    open fun nullSafe(): String {
        var b: String? = "xxx"
//        b = null

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
         * Elvis 操作符
         *
         */
        Log.d(TAG, b?.length.toString() ?: "null")
        try {
            Log.d(TAG, "nullSafe: ${elvis()}")
        } catch (e: KotlinNullPointerException) {
            Log.e(TAG, "nullSafe: ", e)
        }

        /**
         * !!操作符
         */
        var person = Person(null, 10)
        try {
            val name = person!!.student!!.name
            Log.d(TAG, "nullSafe: $name")
        } catch (e: KotlinNullPointerException) {
            Log.e(TAG, "nullSafe: ", e)
        }

        val str1 = b?.length.toString()
        val let = str1?.let { Log.d(TAG, "nullSafe: is null") }
        Log.d(TAG, "nullSafe: $let")
//        Log.d(TAG, str1?.let { Log.d(TAG, "nullSafe: is null") })

        /**
         * TODO 问题 as? 为什么不能转换
         */
        var a: Int? = 1.2f as? Int
//        if (a != null) a as String else "null"
        Log.d(TAG, a?.toString() ?: "null")

        return ""
    }

    private fun elvis(): String {
        var person = Person(Student(null), 10)
        val name = person?.student?.name ?: "default name"

        /**
         *  throw 和 return 在 Kotlin 中都是表达式，所以它们也可以用在 elvis 操作符右侧
         */
//        val name2 = person?.student?.name ?: throw KotlinNullPointerException()
//        val name3 = person?.student?.name ?: return "default name"
        Log.d(TAG, "nullSafe: $name")
        return name
    }

    open fun range() {
        for (i in 1..10) {
            Log.d(TAG, "range: $i")
        }

        Log.d(TAG, "--------------------------")

        for (i in 10 downTo 1) {
            Log.d(TAG, "range: $i")
        }

        Log.d(TAG, "--------------------------")

        /**
         * 跳过指定步长
         */
        for (i in 1..10 step 4) {
            Log.d(TAG, "range: $i")
        }

        Log.d(TAG, "--------------------------")

        for (i in 10 downTo 1 step 2) {
            Log.d(TAG, "range: $i")
        }

        Log.d(TAG, "--------------------------")

        /**
         * 不包括其结束元素的区间
         */
        for (i in 1 until 10) {
            Log.d(TAG, "range: $i")
        }
    }

    private fun test(string: String) {
        Log.d(TAG, "test: $string")
    }

    private class Person(stu: Student?, ag: Int) : Any() {
        val student = stu
        val age = ag
    }

    private class Student(name: String?) {
        val name = name
    }

    private fun methodTest(x:Int=1,y:Int=2,z:String="三") {
        Log.d(TAG, "methodTest: $x , $y , $z")
    }

    /**
     * 可变数量参数
     */
    private fun <T>vararg(vararg ts :T){
        for (i in ts){
            Log.d(TAG, "vararg: $i")
        }
    }

    /**
     * 局部函数，即一个函数在另一个函数内部
     *
     * 局部函数可以访问外部函数 如示例中的count
     */
    private fun 局部函数(){
        var count = 0
        fun count(){
            for (i in 1..10){
                count++
            }
        }
        for (i in 1..2){
            count()
        }
        Log.d(TAG, "局部函数: $count")
    }

}