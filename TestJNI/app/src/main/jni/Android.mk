LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE    := NDKDemo
LOCAL_C_INCLUDES+= $(LOCAL_PATH)
SRC_FILES := $(wildcard $(LOCAL_PATH)/*.cpp)
SRC_FILES := $(SRC_FILES:$(LOCAL_PATH)/%=%)

LOCAL_SRC_FILES := $(SRC_FILES)

#LOCAL_LDLIBS    := -llog -landroid
LOCAL_LDLIBS    := -llog

include $(BUILD_SHARED_LIBRARY)