package com.skynet.fish_shop.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CartProduct.class, FavoritesProduct.class, User.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CartProductDao cartProductDao();
    public abstract FavoritesProductDao favoritesProductDao();
    public abstract UserDao userDao();
}
