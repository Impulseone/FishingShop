package com.skynet.fishingshop.model;

import com.google.firebase.database.PropertyName;

public class Product {

    @PropertyName("id")
    public String id;
    @PropertyName("Название")
    public String name;
    @PropertyName("Описание")
    public String description;
    @PropertyName("Цена")
    public int price;
    @PropertyName("Скидка")
    public int discount;
    @PropertyName("Статус")
    public String status;
    @PropertyName("Изображение")
    public String imagePath;

    public Product() {
    }

    public Product(String id, String name, String description, int price, int discount, String status, String imagePath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.discount = discount;
        this.imagePath = imagePath;
    }
}
