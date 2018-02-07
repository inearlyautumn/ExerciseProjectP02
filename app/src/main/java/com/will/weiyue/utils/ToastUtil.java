package com.will.weiyue.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * author: liweixing
 * date: 2018/2/7
 */

public class ToastUtil {

    private static boolean isShow = true;

    /**
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

}
