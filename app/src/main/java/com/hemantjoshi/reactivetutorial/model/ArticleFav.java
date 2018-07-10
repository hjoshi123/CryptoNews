package com.hemantjoshi.reactivetutorial.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "ArticleFav")
public class ArticleFav {

    @PrimaryKey (autoGenerate = true)
    private int mId;

    @ColumnInfo (name = "title")
    private String mTitle;

    @ColumnInfo (name = "description")
    private String mDescription;

    @ColumnInfo (name = "url")
    private String mUrl;

    @ColumnInfo(name = "checkbox")
    private boolean checkbox;

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public boolean getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }
}
