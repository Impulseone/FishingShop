package com.skynet.fish_shop.model;

import com.google.firebase.database.PropertyName;

public class Product {

    @PropertyName("id")
    public String id;
    @PropertyName("Название")
    public String name;
    @PropertyName("Подкатегория")
    public String subCategory;
    @PropertyName("Описание")
    public String description;
    @PropertyName("Цена")
    public int price;
    @PropertyName("Скидка")
    public int discount;
    @PropertyName("Статус")
    public String status;
    @PropertyName("Изображение")
    public String imagesPaths;

    public Product() {
    }

    public Product(String id, String name, String subCategory, String description, int price, int discount, String status, String imagesPaths) {
        this.id = id;
        this.name = name;
        this.subCategory = subCategory;
        this.description = description;
        this.price = price;
        this.status = status;
        this.discount = discount;
        this.imagesPaths = imagesPaths;
    }
}
