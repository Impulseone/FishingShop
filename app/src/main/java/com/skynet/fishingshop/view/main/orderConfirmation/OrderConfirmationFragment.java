package com.skynet.fishingshop.view.main.orderConfirmation;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.skynet.fishingshop.App;
import com.skynet.fishingshop.R;
import com.skynet.fishingshop.db.User;
import com.skynet.fishingshop.view.main.home.HomeFragment;
import com.skynet.fishingshop.view.main.profile.ProfileFragment;
import com.skynet.fishingshop.view.main.cart.CartFragment;

import java.util.List;

public class OrderConfirmationFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        bottomNavigationView = this.getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.GONE);

        view = inflater.inflate(R.layout.fragment_order_confirmation, container, false);
        setButtons();

        new CheckUserData(view).execute();

        return view;
    }

    private void setButtons(){
        setBackToCartButton();
        setBackToProfileButton();
        setConfirmButton();
        setGetFromShopButton();
    }

    private void setBackToCartButton(){
        View backToCart = view.findViewById(R.id.back_button);
        backToCart.setOnClickListener(view1 -> backToCart());
    }

    private void setBackToProfileButton(){
        View backToProfile = view.findViewById(R.id.profile_button);
        backToProfile.setOnClickListener(view1 -> backToProfile());
    }

    private void setGetFromShopButton(){
        view.findViewById(R.id.get_from_shop_button).setOnClickListener(view -> {
            Toast.makeText(view.getContext(),"Заказ оформлен",Toast.LENGTH_SHORT).show();
            backToHome();
        });
    }

    private void setConfirmButton(){
        View confirmButton = view.findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(view1 -> {
            Toast.makeText(view.getContext(),"Заказ оформлен",Toast.LENGTH_SHORT).show();
            backToHome();
        });
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
            List<User> userList = App.getInstance().getDatabase().userDao().getAll();
            if (userList.size() != 0) {
                user = userList.get(0);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (user == null) {
                view.findViewById(R.id.profile_button).setVisibility(View.VISIBLE);
            }
            else {
                view.findViewById(R.id.profile_button).setVisibility(View.GONE);
            }
        }
    }
}