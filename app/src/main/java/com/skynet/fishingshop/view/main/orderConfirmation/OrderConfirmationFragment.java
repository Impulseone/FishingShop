package com.skynet.fishingshop.view.main.orderConfirmation;

import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skynet.fishingshop.App;
import com.skynet.fishingshop.R;
import com.skynet.fishingshop.db.CartProduct;
import com.skynet.fishingshop.db.User;
import com.skynet.fishingshop.model.DeliveryData;
import com.skynet.fishingshop.model.OrderKeeper;
import com.skynet.fishingshop.view.extension.WarningDialogView;
import com.skynet.fishingshop.view.main.MainActivity;
import com.skynet.fishingshop.view.main.home.HomeFragment;
import com.skynet.fishingshop.view.main.profile.ProfileFragment;
import com.skynet.fishingshop.view.main.cart.CartFragment;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderConfirmationFragment extends Fragment {

    private OrderKeeper orderKeeper = OrderKeeper.getInstance();

    private BottomNavigationView bottomNavigationView;
    private View view;
    private static boolean needToFillUserData = false;

    private final List<CartProduct> products;

    private EditText region;
    private EditText index;
    private EditText city;
    private EditText street;

    private boolean isDeliveryNeeded = true;

    public OrderConfirmationFragment(List<CartProduct> products) {
        this.products = products;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        bottomNavigationView = this.getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.GONE);

        view = inflater.inflate(R.layout.fragment_order_confirmation, container, false);
        setButtons();
        setViews();

        new CheckUserData(view).execute();

        return view;
    }

    private void setViews() {
        region = view.findViewById(R.id.region);
        index = view.findViewById(R.id.index);
        city = view.findViewById(R.id.city);
        street = view.findViewById(R.id.street);
    }

    private void setButtons() {
        setBackToCartButton();
        setBackToProfileButton();
        setConfirmButton();
        setGetFromShopButton();
    }

    private void setBackToCartButton() {
        View backToCart = view.findViewById(R.id.back_button);
        backToCart.setOnClickListener(view1 -> backToCart());
    }

    private void setBackToProfileButton() {
        View backToProfile = view.findViewById(R.id.profile_button);
        backToProfile.setOnClickListener(view1 -> backToProfile());
    }

    private void setGetFromShopButton() {
        View getFromShopButton = view.findViewById(R.id.get_from_shop_button);
        getFromShopButton.setOnClickListener(view -> {
            isDeliveryNeeded = !isDeliveryNeeded;
            if (isDeliveryNeeded)
                getFromShopButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow_transparent, getContext().getTheme())));
            else
                getFromShopButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow_transparent_2, getContext().getTheme())));
        });
    }

    private void setConfirmButton() {
        View confirmButton = view.findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(view1 -> {
            if (needToFillUserData) {
                new WarningDialogView("Ошибка", "Заполните данные в профиле").show(getActivity().getSupportFragmentManager(), "");
            } else {
                if (isDeliveryNeeded) {
                    if (checkDeliveryDataFields()) setDeliveryData();
                    else {
                        new WarningDialogView("Ошибка", "Заполните данные доставки").show(getActivity().getSupportFragmentManager(), "");
                        return;
                    }
                }
                orderKeeper.setDeliveryNeed(isDeliveryNeeded);
                orderKeeper.setProductsFromCart(products);
                orderKeeper.setPrice(calculatePrice());
                ((MainActivity) getActivity()).purchase(products);
            }
        });
    }

    private boolean checkDeliveryDataFields() {
        return checkParameter(region.getText().toString()) && checkParameter(index.getText().toString()) && checkParameter(city.getText().toString()) && checkParameter(street.getText().toString());
    }

    private boolean checkParameter(String parameter) {
        return parameter != null && !parameter.equals("");
    }

    private int calculatePrice() {
        int allPrice = 0;
        for (CartProduct cartProduct : products) {
            allPrice += cartProduct.price * cartProduct.count;
        }
        return allPrice;
    }

    private void setDeliveryData() {
        DeliveryData deliveryData = new DeliveryData();
        deliveryData.setCity(city.getText().toString());
        deliveryData.setIndex(index.getText().toString());
        deliveryData.setRegion(region.getText().toString());
        deliveryData.setStreet(street.getText().toString());
        OrderKeeper.getInstance().setDeliveryData(deliveryData);
    }

    private void backToCart() {
        bottomNavigationView.setVisibility(View.VISIBLE);
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        CartFragment cartFragment = new CartFragment();
        ft.replace(R.id.main_relative_layout, cartFragment);
        ft.commit();
    }

    private void backToProfile() {
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.getMenu().setGroupCheckable(0, false, true);
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ProfileFragment profileFragment = new ProfileFragment();
        ft.replace(R.id.main_relative_layout, profileFragment);
        ft.commit();
    }

    private void backToHome() {
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.getMenu().setGroupCheckable(0, false, true);
        bottomNavigationView.setSelectedItemId(R.id.action_main);
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        ft.replace(R.id.main_relative_layout, homeFragment);
        ft.commit();
    }

    private static class CheckUserData extends AsyncTask<Void, Void, Void> {

        private User user;
        private View view;

        private CheckUserData(View view) {
            this.view = view;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<User> usersList = App.getInstance().getDatabase().userDao().getAll();
            if (usersList.size() != 0) {
                user = usersList.get(0);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (user == null) {
                view.findViewById(R.id.profile_button).setVisibility(View.VISIBLE);
                needToFillUserData = true;
            } else {
                view.findViewById(R.id.profile_button).setVisibility(View.GONE);
                needToFillUserData = false;
                OrderKeeper.getInstance().setUser(user);
            }
        }
    }
}