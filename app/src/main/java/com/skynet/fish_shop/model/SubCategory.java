package com.skynet.fish_shop.model;

import java.util.HashMap;

public class SubCategory {
   private String id;
   private String name;

    public SubCategory(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
