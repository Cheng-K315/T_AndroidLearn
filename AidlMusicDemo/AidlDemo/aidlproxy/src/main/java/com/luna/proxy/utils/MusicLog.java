package com.luna.proxy.utils;

import android.util.Log;

public class MusicLog {
    private static boolean isDebug = true;
    private static final String PreTag = "AidlLearn";

    public static void w(String tag, String msg) {
        if (isDebug) {
            Log.w(PreTag + tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(PreTag + tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.d(PreTag + tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(PreTag + tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (isDebug) {
            Log.v(PreTag + tag, msg);
        }
    }
}
