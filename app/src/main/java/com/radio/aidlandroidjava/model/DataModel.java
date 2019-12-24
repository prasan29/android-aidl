package com.radio.aidlandroidjava.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public final class DataModel implements Parcelable {
    public static final Creator<DataModel> CREATOR = new Creator<DataModel>() {
        @Override
        public DataModel createFromParcel(Parcel in) {
            return new DataModel(in);
        }

        @Override
        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }
    };
    private String mName;
    private int mAge;
    private List<String> mList;
    private boolean mIsAlive;

    private DataModel(Parcel in) {
        mName = in.readString();
        mAge = in.readInt();
        mList = in.createStringArrayList();
        mIsAlive = in.readByte() != 0;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public List<String> getList() {
        return mList;
    }

    public void setList(List<String> list) {
        mList = list;
    }

    public boolean isAlive() {
        return mIsAlive;
    }

    public void setAlive(boolean alive) {
        mIsAlive = alive;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeInt(mAge);
        parcel.writeList(mList);
        if (mIsAlive) {
            parcel.writeByte((byte) 1);
        } else {
            parcel.writeByte((byte) 0);
        }
    }
}
