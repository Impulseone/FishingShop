package com.skynet.fishingshop.model;

import com.google.firebase.database.PropertyName;

public class Product {

    @PropertyName("Название")
    public String name;
    @PropertyName("Описание")
    public String description;
    @PropertyName("Цена")
    public int price;
    @PropertyName("Скидка")
    public int discount;
    @PropertyName("Изображение")
    public String imagePath;

    public Product() {
    }

    public Product(String name, String description, int price, int discount, String imagePath) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.imagePath = imagePath;
    }
}
