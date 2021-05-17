package com.skynet.fish_shop.extension;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skynet.fish_shop.App;
import com.skynet.fish_shop.R;
import com.skynet.fish_shop.db.CartProduct;
import com.skynet.fish_shop.db.User;
import com.skynet.fish_shop.model.DeliveryData;
import com.skynet.fish_shop.model.OrderKeeper;
import com.skynet.fish_shop.view.main.cart.CartFragment;
import com.skynet.fish_shop.view.main.cart.CartProductsAdapter;
import com.skynet.fish_shop.view.main.home.HomeFragment;
import com.skynet.fish_shop.view.main.orderConfirmation.OrderConfirmationFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OrdersHandler {

    private final DatabaseReference ordersReference = FirebaseDatabase.getInstance().getReference("Заказы");
    private final OrderKeeper orderKeeper = OrderKeeper.getInstance();

    private final BottomNavigationView bottomNavigationView;
    private final FragmentManager fragmentManager;

    public OrdersHandler(BottomNavigationView bottomNavigationView, FragmentManager fragmentManager) {
        this.bottomNavigationView = bottomNavigationView;
        this.fragmentManager = fragmentManager;
    }

    public void purchase() {
        ordersReference.updateChildren(createOrder());
        orderKeeper.clear();
        createHomeFragmentFromCart();
        Toast.makeText(bottomNavigationView.getContext(), "Заказ оформлен", Toast.LENGTH_SHORT).show();
        new ClearCartProductsTask().execute();
    }

    private Map<String, Object> createOrder() {
        Map<String, Object> order = new HashMap<>();
        orderKeeper.setOrderId((new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().getTime())));
        order.put("Заказ " + orderKeeper.getOrderId(), createOrderBody());
        return order;
    }

    private Map<String, Object> createOrderBody() {
        Map<String, Object> orderBody = new HashMap<>();
        User user = orderKeeper.getUser();
        orderBody.put("ФИО", user.lastName + " " + user.firstName + " " + user.thirdName);
        orderBody.put("Телефон", user.phoneNumber);
        orderBody.put("Почта", user.email);
        if (orderKeeper.isDeliveryNeed()) {
            DeliveryData deliveryData = orderKeeper.getDeliveryData();
            orderBody.put("Доставка", "Да");
            orderBody.put("Регион", deliveryData.getRegion());
            orderBody.put("Индекс", deliveryData.getIndex());
            orderBody.put("Город", deliveryData.getCity());
            orderBody.put("Улица, Дом, Квартира", deliveryData.getStreet());
        } else {
            orderBody.put("Доставка", "Нет");
        }
        orderBody.put("Общая сумма заказа", orderKeeper.getPrice() + " руб.");
        orderBody.put("Товары", orderKeeper.getProducts());
        return orderBody;
    }

    private void createHomeFragmentFromCart() {
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.getMenu().setGroupCheckable(0, false, true);
        bottomNavigationView.setSelectedItemId(R.id.action_main);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        ft.replace(R.id.main_relative_layout, homeFragment);
        ft.commit();
    }

    static class ClearCartProductsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            App.getInstance().getDatabase().cartProductDao().clearTable();
            return null;
        }
    }
}
