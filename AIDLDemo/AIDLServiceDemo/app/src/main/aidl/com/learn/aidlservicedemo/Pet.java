package com.learn.aidlservicedemo;

import android.os.Parcel;
import android.os.Parcelable;

public class Pet implements Parcelable
{
    private String name;
    private double weight;
    public Pet()
    {
    }
    public Pet(String name, double weight)
    {
        super();
        this.name = name;
        this.weight = weight;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public double getWeight()
    {
        return weight;
    }
    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }
    /* (non-Javadoc)
     * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
     */
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        //把该对象所包含的数据写到Parcel
        dest.writeString(name);
        dest.writeDouble(weight);
    }

    // 添加一个静态成员,名为CREATOR,该对象实现了Parcelable.Creator接口
    public static final Parcelable.Creator<Pet> CREATOR
            = new Parcelable.Creator<Pet>()
    {
        @Override
        public Pet createFromParcel(Parcel source)
        {
            // 从Parcel中读取数据，返回Person对象
            return new Pet(source.readString()
                    , source.readDouble());
        }

        @Override
        public Pet[] newArray(int size)
        {
            return new Pet[size];
        }
    };
    @Override
    public String toString()
    {
        return "个人信息 [name=" + name + ", weight=" + weight + "]";
    }
}