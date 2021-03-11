package com.skynet.fishingshop.extension;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.skynet.fishingshop.model.Category;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CategoriesKeeper {
    private static CategoriesKeeper instance;
    private List<Category> categories;

    private CategoriesKeeper(List<Category> categories) {
        this.categories = categories;
    }

    public static CategoriesKeeper getInstance() {
        if (instance == null) {
            instance = new CategoriesKeeper(new ArrayList<>());
        }
        return instance;
    }

    public List<Category> getCategories() {
        return categories;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void clear() {
        instance = new CategoriesKeeper(new ArrayList<>());
    }
}
