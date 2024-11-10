// Novel.java
package com.example.feedback1_aplicaciondegestiondenovelass.modelo;

import android.os.Parcel;
import android.os.Parcelable;

public class Novel implements Parcelable {
    private String key;
    private String title;
    private String author;
    private int year;
    private String synopsis;
    private boolean isFavorite;

    public Novel() {
        // Default constructor required for calls to DataSnapshot.getValue(Novel.class)
    }

    public Novel(String title, String author, int year, String synopsis) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.synopsis = synopsis;
        this.isFavorite = false;
    }

    protected Novel(Parcel in) {
        key = in.readString();
        title = in.readString();
        author = in.readString();
        year = in.readInt();
        synopsis = in.readString();
        isFavorite = in.readByte() != 0;
    }

    public static final Creator<Novel> CREATOR = new Creator<Novel>() {
        @Override
        public Novel createFromParcel(Parcel in) {
            return new Novel(in);
        }

        @Override
        public Novel[] newArray(int size) {
            return new Novel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeInt(year);
        dest.writeString(synopsis);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters and setters
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public String getSynopsis() { return synopsis; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }
    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
}