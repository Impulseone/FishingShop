package com.skynet.fish_shop.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.skynet.fish_shop.model.Product;

@Entity
public class FavoritesProduct {
    @PrimaryKey
    @NonNull
    public String id;
    public String name;
    public String description;
    public int price;
    public int discount;
    public String imagePath;

    public FavoritesProduct(@NonNull String id) {
        this.id = id;
    }

    public FavoritesProduct(Product product) {
        this.id = product.id;
        this.name = product.name;
        this.description = product.description;
        this.price = product.price;
        this.discount = product.discount;
        this.imagePath = product.imagePath;
    }
}
