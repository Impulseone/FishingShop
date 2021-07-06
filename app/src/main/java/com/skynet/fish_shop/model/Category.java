package com.skynet.fish_shop.model;

import java.util.List;

public class Category {
    private final String name;
    private final List<Product> productList;

    public Category(String categoryName, List<Product> productList) {
        this.name = categoryName;
        this.productList = productList;
    }

    public String getName() {
        return name;
    }

    public List<Product> getProductList() {
        return productList;
    }
}
