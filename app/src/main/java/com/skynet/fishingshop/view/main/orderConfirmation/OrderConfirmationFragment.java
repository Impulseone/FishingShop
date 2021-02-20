package com.skynet.fishingshop.view.main.orderConfirmation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.skynet.fishingshop.R;
import com.skynet.fishingshop.view.main.HomeFragment;
import com.skynet.fishingshop.view.main.ProfileFragment;
import com.skynet.fishingshop.view.main.cart.CartFragment;

public class OrderConfirmationFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        bottomNavigationView = this.getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.GONE);

        View view = inflater.inflate(R.layout.fragment_order_confirmation, container, false);
        setButtons(view);

        return view;
    }

    private void setButtons(View view){
        setBackToCartButton(view);
        setBackToProfileButton(view);
        setBackToHomeButton(view);
    }

    private void setBackToCartButton(View view){
        View backToCart = view.findViewById(R.id.back_button);
        backToCart.setOnClickListener(view1 -> backToCart());
    }

    private void setBackToProfileButton(View view){
        View backToProfile = view.findViewById(R.id.profile_button);
        backToProfile.setOnClickListener(view1 -> backToProfile());
    }

    private void setBackToHomeButton(View view){
        View backToHome = view.findViewById(R.id.confirm_button);
        backToHome.setOnClickListener(view1 -> backToHome());
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
}