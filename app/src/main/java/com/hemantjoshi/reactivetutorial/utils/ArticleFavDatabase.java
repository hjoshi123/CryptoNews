package com.hemantjoshi.reactivetutorial.utils;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.hemantjoshi.reactivetutorial.model.ArticleFav;

@Database(entities = {ArticleFav.class}, version = ArticleFavDatabase.VERSION)
public abstract class ArticleFavDatabase extends RoomDatabase{
    static final int VERSION = 1;
    public abstract ArticleFavDao getArticleFavDao();
}
