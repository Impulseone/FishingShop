package com.skynet.fishingshop.model;

import java.util.Map;

public class Product {
    private String name;
    private String description;
    private int price;
    private int discount;

    public Product(String name, String description, int price, int discount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
    }

    public static Product fromJson(Map<String, Object> json) {
        return new Product(json.get("Название").toString(), json.get("Описание").toString(), Integer.parseInt(json.get("Цена").toString()), Integer.parseInt(json.get("Скидка").toString()));
    }
}
