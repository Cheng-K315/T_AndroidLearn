package com.learn.testjni_cmake;

public class JNIUtils {
    public static final String info = "Welcome to C+++++++++!";
    public static final int number = 5;
    public static native String hello();
    static {
        System.loadLibrary("NDKDemo");
    }

    public static String hello2(){
        return info + number;
    }
}
