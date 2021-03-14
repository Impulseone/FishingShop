package com.skynet.fishingshop.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavoritesProductDao {
    @Query("SELECT * FROM FavoritesProduct")
    List<FavoritesProduct> getAll();

    @Query("SELECT * FROM FavoritesProduct WHERE id = :id")
    FavoritesProduct getById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoritesProduct favoritesProduct);

    @Update
    void update(FavoritesProduct favoritesProduct);

    @Delete
    void delete(FavoritesProduct favoritesProduct);

    @Query("DELETE FROM FavoritesProduct")
    void clearTable();
}
