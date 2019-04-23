package com.example.annamihaleva.abbyy.objects;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Item implements Parcelable {

    Item(Parcel parcel){
        String[] res = new String[2];
        parcel.readStringArray(res);
        this.tags = parcel.readArrayList(null);
        this.title = res[0];
        this.link = res[1];
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getTags() {

        return tags;
    }

    public String getLink() {
        return link;
    }

    private ArrayList<String> tags;
    private String link;
    private String title;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(tags);
        String[] res = {title, link};
        dest.writeStringArray(res);
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
