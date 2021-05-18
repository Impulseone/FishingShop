package com.skynet.fish_shop.model;

import java.util.List;

public class Category {
    private final String categoryName;
    private final List<Product> productList;

    public Category(String categoryName, List<Product> productList) {
        this.categoryName = categoryName;
        this.productList = productList;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<Product> getProductList() {
        return productList;
    }
}
