package com.fighter_lee.kotlinlearn

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var x: Int = 1
        var Y: Float = 1.1F
        var z = 2L
        var c = false
        var zz = x.toChar()
        println(x)
        println(Y)
        println(zz)
        println(""+Int.MAX_VALUE + "__"+Int.MIN_VALUE)
        println("----------------")
        var stringa = "xyzxxxxx"
        println(stringa.length)
        println(stringa.get(0))
        println(stringa)
        println("\'")
        var text = """
xxx
xxx
yyy
"""
        println(text)
        for (i in 1.rangeTo(10)){
            //1 -- 10
            println(i)
        }

        for (i in 1.until(10)){
            //1 -- 9  [1,9)
            println(i)
        }

        for (i in 10 downTo 1){
            //10 -- 1
            println(i)
        }

        //String? 声明一个可空对象
        var b : String?= null
        //b?.length  b != null --> b.length  a?:b --> a=null-->返回b
        var bLength = b?.length?:-1
        println(bLength)
    }
}
