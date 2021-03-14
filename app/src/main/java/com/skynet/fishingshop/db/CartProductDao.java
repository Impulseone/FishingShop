package com.skynet.fishingshop.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartProductDao {

    @Query("SELECT * FROM CartProduct")
    List<CartProduct> getAll();

    @Query("SELECT * FROM cartproduct WHERE id = :id")
    CartProduct getById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CartProduct cartProduct);

    @Update
    void update(CartProduct cartProduct);

    @Delete
    void delete(CartProduct cartProduct);

    @Query("DELETE FROM CartProduct")
    void clearTable();
}
