package com.skynet.fish_shop.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.skynet.fish_shop.model.Product;

@Entity
public class CartProduct {
    @PrimaryKey
    @NonNull
    public String id;
    public String name;
    public String description;
    public int price;
    public int discount;
    public String imagePath;
    public int count;

    public CartProduct(@NonNull String id) {
        this.id = id;
    }

    public CartProduct(Product product) {
        this.id = product.id;
        this.name = product.name;
        this.description = product.description;
        this.price = product.price;
        this.discount = product.discount;
        this.imagePath = product.imagePath;
        this.count = 1;
    }

    public CartProduct(FavoritesProduct product) {
        this.id = product.id;
        this.name = product.name;
        this.description = product.description;
        this.price = product.price;
        this.discount = product.discount;
        this.imagePath = product.imagePath;
        this.count = 1;
    }
}
