package com.fighter_lee.kotlinlearn;

/**
 * @author fighter_lee
 * @date 2017/12/7
 * @describe 常规的java接口回调
 */
public class Bb {

    private BBB mList;

    interface BBB {
        void onClick(int position,int ttt);

//        int xxx(int ccc);
    }

    public void  setListener(BBB bbb) {
        this.mList = bbb;
    }

}
