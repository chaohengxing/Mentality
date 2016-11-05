package com.agjsj.mentality.utils;

import android.graphics.Color;

import java.security.SecureRandom;

/**
 * @author smile
 * @project Util
 * @date 2016-03-01-14:55
 */
public class Util {
    public static boolean checkSdCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    /**
     * 随机获取一个颜色，用于测试
     *
     * @return
     */
    public static int getRandomColor() {
        SecureRandom rgen = new SecureRandom();
        return Color.HSVToColor(150, new float[]{
                rgen.nextInt(359), 1, 1
        });
    }
}
