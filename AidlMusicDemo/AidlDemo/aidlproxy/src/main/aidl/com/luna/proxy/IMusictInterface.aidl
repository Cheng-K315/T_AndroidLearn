// IMusictInterface.aidl
package com.luna.proxy;
import com.luna.proxy.bean.MusicBean;
import com.luna.proxy.IMusicCalllback;
interface IMusictInterface {
    MusicBean getMusicBean();
    void setProgress(int progress);
    void registerCallback(IMusicCalllback callback);
    void unRegisterCallback(IMusicCalllback callback);
    void play();
    void stop();
}