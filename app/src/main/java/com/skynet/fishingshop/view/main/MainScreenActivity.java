package com.skynet.fishingshop.view.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.skynet.fishingshop.R;
import com.skynet.fishingshop.view.main.cart.CartFragment;
import com.skynet.fishingshop.view.main.catalog.CatalogFragment;

public class MainScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar)findViewById(R.id.tool_bar));

        createHomeFragment();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_main:
                    createHomeFragment();
                    break;
                case R.id.action_catalog:
                    createCatalogFragment();
                    break;
                case R.id.action_cart:
                    createCartFragment();
                    break;
            }
            return true;
        });
    }

//    private void createHomeFragment() {
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        HomeFragment homeFragment = new HomeFragment();
//        ft.replace(R.id.main_relative_layout, homeFragment);
//        ft.commit();
//    }

    private void createHomeFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ProfileFragment homeFragment = new ProfileFragment();
        ft.replace(R.id.main_relative_layout, homeFragment);
        ft.commit();
    }

    private void createCatalogFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        CatalogFragment catalogFragment = new CatalogFragment();
        ft.replace(R.id.main_relative_layout, catalogFragment);
        ft.commit();
    }

    private void createCartFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_relative_layout, new CartFragment());
        ft.commit();
    }

}