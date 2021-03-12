package com.skynet.fishingshop.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM CartProduct")
    List<CartProduct> getAll();

    @Query("SELECT * FROM cartproduct WHERE id = :id")
    CartProduct getById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CartProduct cartProduct);

    @Update
    void update(CartProduct employee);

    @Delete
    void delete(CartProduct employee);
}
