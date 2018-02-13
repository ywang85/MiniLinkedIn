package com.example.wyj.minilinkedin.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by wyj on 2018/2/11.
 */

public class Education implements Parcelable{
    public String id;
    public String school;
    public String major;
    public Date startDate;
    public Date endDate;
    public List<String> course;
    public Education() {
        this.id = UUID.randomUUID().toString();
    }

    protected Education(Parcel in) {
        id = in.readString();
        school = in.readString();
        major = in.readString();
        startDate = new Date(in.readLong());
        endDate = new Date(in.readLong());
        course = in.createStringArrayList();
    }

    public static final Creator<Education> CREATOR = new Creator<Education>() {
        @Override
        public Education createFromParcel(Parcel in) {
            return new Education(in);
        }

        @Override
        public Education[] newArray(int size) {
            return new Education[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(school);
        parcel.writeString(major);
        parcel.writeLong(startDate.getTime());
        parcel.writeLong(endDate.getTime());
        parcel.writeStringList(course);
    }
}
