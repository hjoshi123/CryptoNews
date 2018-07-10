package com.hemantjoshi.reactivetutorial.utils;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.hemantjoshi.reactivetutorial.model.ArticleFav;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ArticleFavDao {
    @Insert
    void insertArticleIntoFav(ArticleFav... articles);

    @Query("DELETE FROM ArticleFav WHERE title LIKE :title")
    void delete(String title);

    @Query("SELECT * FROM ArticleFav")
    Flowable<List<ArticleFav>> getFavArticles();
}
