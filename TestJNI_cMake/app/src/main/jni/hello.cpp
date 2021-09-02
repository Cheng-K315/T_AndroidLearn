//
// Created by WTCL on 2021/8/18.
//
#include "com_learn_testjni_cmake_JNIUtils.h"

extern "C" JNIEXPORT jstring JNICALL Java_com_learn_testjni_1cmake_JNIUtils_hello
  (JNIEnv *env, jclass jclazz){
    jmethodID method_id = env->GetStaticMethodID(jclazz,"hello2","()Ljava/lang/String;");
    jfieldID field_id = env->GetStaticFieldID(jclazz,"info", "Ljava/lang/String;");
    jfieldID field_id1 = env->GetStaticFieldID(jclazz,"number", "I");

//    env->SetStaticObjectField(jclazz, field_id, (jobject) "Welcome to C++!");
//    env->SetStaticIntField(jclazz, field_id1, 10);

    jstring  jstring1 = (jstring)(env)->CallStaticObjectMethod(jclazz,method_id);
    jstring jstring2 = (jstring)(env)->GetStaticObjectField(jclazz,field_id);
    char *print = (char*) env->GetStringUTFChars(jstring1, 0);

//    jint jint1 = (jint)env->GetStaticIntField(jclazz, field_id1);
//    char *number = (char*) env->GetStringChars((jstring) jint1, 0);

    return env->NewStringUTF(print);
}
