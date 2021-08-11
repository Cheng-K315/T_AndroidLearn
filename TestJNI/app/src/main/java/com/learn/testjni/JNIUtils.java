package com.learn.testjni;

public class JNIUtils {
    public native String hello();
    static {
        System.loadLibrary("NDKDemo");
    }
}
