package com.luna.proxy.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class MusicBean implements Parcelable {

   private float duration;
   private int progress;
   private String album;


   public  MusicBean(float duration,int progress,String album){
       this.duration = duration;
       this.progress = progress;
       this.album = album;
   }

    protected MusicBean(Parcel parcel) {
        duration = parcel.readFloat();
        progress = parcel.readInt();
        album = parcel.readString();

    }

    public String getAlbum(){
       return album;
    }

    public int getProgress(){
       return progress;
    }

    public float getDuration(){
       return duration;
    }

    public static final Creator<MusicBean> CREATOR = new Creator<MusicBean>() {
        @Override
        public MusicBean createFromParcel(Parcel in) {
            return new MusicBean(in);
        }

        @Override
        public MusicBean[] newArray(int size) {
            return new MusicBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(duration);
        dest.writeInt(progress);
        dest.writeString(album);
    }
}
