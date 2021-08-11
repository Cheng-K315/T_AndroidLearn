//
// Created by WTCL on 2021/8/11.
//
#include "com_learn_testjni_JNIUtils.h"

JNIEXPORT jstring JNICALL Java_com_learn_testjni_JNIUtils_hello
  (JNIEnv *env, jobject obj){
  return (*env)->NewStringUTF(env,"String from C");
  }

