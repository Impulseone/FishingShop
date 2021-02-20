package com.skynet.fishingshop.view.main.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.skynet.fishingshop.R;
import com.skynet.fishingshop.view.main.home.HomeFragment;

public class ProfileFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        bottomNavigationView = this.getActivity().findViewById(R.id.bottom_navigation);
        setBackToHomeButton(view);
        return view;
    }

    private void setBackToHomeButton(View view) {
        View backToHome = view.findViewById(R.id.confirm_button);
        backToHome.setOnClickListener(view1 -> backToHome());
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