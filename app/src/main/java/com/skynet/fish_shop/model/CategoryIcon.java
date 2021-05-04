package com.skynet.fish_shop.model;

public class CategoryIcon {
    private final String categoryName;
    private final String imagePath;

    public CategoryIcon(String categoryName, String imagePath) {
        this.categoryName = categoryName;
        this.imagePath = imagePath;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getImagePath() {
        return imagePath;
    }
}
