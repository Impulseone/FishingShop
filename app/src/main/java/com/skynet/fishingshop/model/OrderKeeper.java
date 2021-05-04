package com.skynet.fishingshop.model;

import com.skynet.fishingshop.db.CartProduct;
import com.skynet.fishingshop.db.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderKeeper {

    private static OrderKeeper instance;

    private String orderId;
    private User user;
    private DeliveryData deliveryData;
    private int price;
    private boolean isDeliveryNeed;
    private final Map<String, String> products;

    public OrderKeeper() {
        orderId = "";
        user = new User();
        deliveryData = new DeliveryData();
        price = 0;
        isDeliveryNeed = true;
        products = new HashMap<>();
    }

    public static OrderKeeper getInstance() {
        if (instance == null) instance = new OrderKeeper();
        return instance;
    }

    public DeliveryData getDeliveryData() {
        return deliveryData;
    }

    public void setDeliveryData(DeliveryData deliveryData) {
        this.deliveryData = deliveryData;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addProduct(Product product) {
        products.put(product.id, product.name);
    }

    public void clear() {
        instance = new OrderKeeper();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isDeliveryNeed() {
        return isDeliveryNeed;
    }

    public void setDeliveryNeed(boolean deliveryNeed) {
        isDeliveryNeed = deliveryNeed;
    }

    public Map<String, String> getProducts() {
        return products;
    }

    public void setProducts(List<Product> productsList) {
        for (Product product : productsList) {
            products.put(product.id, product.name);
        }
    }

    public void setProductsFromCart(List<CartProduct> productsList) {
        for (CartProduct product : productsList) {
            products.put(product.id, product.name + " (" + product.count + " шт.)");
        }
    }
}
