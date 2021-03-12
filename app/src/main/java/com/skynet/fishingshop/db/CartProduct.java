package com.skynet.fishingshop.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.PropertyName;
import com.skynet.fishingshop.model.Product;

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
        this.id = product.name.substring(0,3);
        this.name = product.name;
        this.description = product.description;
        this.price = product.price;
        this.discount = product.discount;
        this.imagePath = product.imagePath;
        this.count = 1;
    }
}
