package com.skynet.fish_shop.extension;

import com.skynet.fish_shop.model.Category;
import com.skynet.fish_shop.model.CategoryIcon;
import com.skynet.fish_shop.model.SubCategory;

import java.util.ArrayList;
import java.util.List;

public class CategoriesKeeper {
    private static CategoriesKeeper instance;
    private List<Category> categories;
    private List<SubCategory> subCategories;
    private List<CategoryIcon> categoryIcons;

    private CategoriesKeeper() {
        categories = new ArrayList<>();
        subCategories = new ArrayList<>();
        categoryIcons = new ArrayList<>();
    }

    public static CategoriesKeeper getInstance() {
        if (instance == null) {
            instance = new CategoriesKeeper();
        }
        return instance;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<CategoryIcon> getCategoryIcons() {
        return categoryIcons;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setCategoryIcons(List<CategoryIcon> categoryIcons) {
        this.categoryIcons = categoryIcons;
    }

    public void clear() {
        categories.clear();
        subCategories.clear();
        categoryIcons.clear();
    }
}
