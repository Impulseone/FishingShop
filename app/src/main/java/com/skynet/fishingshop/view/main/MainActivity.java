package com.skynet.fishingshop.view.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skynet.fishingshop.R;
import com.skynet.fishingshop.extension.CategoriesKeeper;
import com.skynet.fishingshop.model.Category;
import com.skynet.fishingshop.model.CategoryIcon;
import com.skynet.fishingshop.model.Product;
import com.skynet.fishingshop.view.authorization.SplashScreenActivity;
import com.skynet.fishingshop.view.extension.LeftNavigationArrayAdapter;
import com.skynet.fishingshop.view.main.cart.CartFragment;
import com.skynet.fishingshop.view.main.catalog.CatalogFragment;
import com.skynet.fishingshop.view.main.favorites.FavoritesFragment;
import com.skynet.fishingshop.view.main.home.HomeFragment;
import com.skynet.fishingshop.view.main.profile.ProfileFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private String[] leftMenuTitlesArray;
    private DrawerLayout mainDrawerLayout;
    private ListView leftMenuTitlesListView;
    private LinearLayout leftMenuLinearLayout;

    private ActionBarDrawerToggle mDrawerToggle;
    private BottomNavigationView bottomNavigationView;

    private DatabaseReference categoriesFromDbReference;
    private DatabaseReference categoriesIconsFromDbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoriesFromDbReference = FirebaseDatabase.getInstance().getReference("Категории");
        categoriesIconsFromDbReference = FirebaseDatabase.getInstance().getReference("Иконки категорий");

        createToolbar();
        createLeftNavigationMenu();
        createHomeFragment();
        createBottomNavigationView();
        getCategories();
        getCategoriesIcons();
    }

    private void createToolbar() {
        Toolbar toolbar = findViewById(R.id.tool_bar);
        toolbar.findViewById(R.id.action_cart).setOnClickListener(view -> {
            createFavoritesFragment();
            leftMenuTitlesListView.setItemChecked(leftMenuTitlesListView.getCheckedItemPosition(), false);
            bottomNavigationView.getMenu().setGroupCheckable(0, false, true);
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void createFavoritesFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FavoritesFragment favoritesFragment = new FavoritesFragment();
        ft.replace(R.id.main_relative_layout, favoritesFragment);
        ft.commit();
    }

    private void createLeftNavigationMenu() {
        leftMenuLinearLayout = (LinearLayout) findViewById(R.id.left_menu_linear_layout);
        mainDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        leftMenuTitlesListView = (ListView) findViewById(R.id.left_drawer);
        leftMenuTitlesArray = getResources().getStringArray(R.array.left_menu_titles);

        leftMenuTitlesListView.setAdapter(new LeftNavigationArrayAdapter(this, leftMenuTitlesArray));
        leftMenuTitlesListView.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mainDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        );
        mainDrawerLayout.setDrawerListener(mDrawerToggle);

        findViewById(R.id.logout_button).setOnClickListener((view -> openSplashScreenActivity()));
    }

    private void createBottomNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            bottomNavigationView.getMenu().setGroupCheckable(0, true, true);
            leftMenuTitlesListView.setItemChecked(leftMenuTitlesListView.getCheckedItemPosition(), false);
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

    private void createHomeFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void openSplashScreenActivity() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, SplashScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        adjustBottomNavigationView();
        checkoutToHomeFragment();
        leftMenuTitlesListView.setItemChecked(leftMenuTitlesListView.getCheckedItemPosition(), false);
    }

    private void adjustBottomNavigationView() {
        findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
        bottomNavigationView.getMenu().setGroupCheckable(0, true, true);
        bottomNavigationView.setSelectedItemId(R.id.action_main);
    }

    private void checkoutToHomeFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        ft.replace(R.id.main_relative_layout, homeFragment);
        ft.commit();
    }

    private void getCategories() {
        categoriesFromDbReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Category> categories = new ArrayList<>();
                for (DataSnapshot category : dataSnapshot.getChildren()) {
                    List<Product> productList = new ArrayList<>();
                    for (DataSnapshot product : category.getChildren()) {
                        productList.add(product.getValue(Product.class));
                    }
                    categories.add(new Category(Objects.requireNonNull(category.getKey()).substring(2), productList));
                }
                CategoriesKeeper.getInstance().setCategories(categories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getMessage());
                System.out.println(error.getDetails());
            }
        });
    }

    private void getCategoriesIcons() {
        categoriesIconsFromDbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<CategoryIcon> categoryIcons = new ArrayList<>();
                for (DataSnapshot categoryIcon : snapshot.getChildren()) {
                    categoryIcons.add(new CategoryIcon(categoryIcon.getKey(), (String) categoryIcon.getValue()));
                }
                CategoriesKeeper.getInstance().setCategoryIcons(categoryIcons);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getMessage());
                System.out.println(error.getDetails());
            }
        });
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
            bottomNavigationView.getMenu().setGroupCheckable(0, false, true);
            checkoutToSelectedFragment(position);
        }
    }

    private void checkoutToSelectedFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new ProfileFragment();
                break;
            case 1:
                fragment = new FavoritesFragment();
                break;
            default:
                break;
        }
        changeFragment(fragment, position);
    }

    private void changeFragment(Fragment fragment, int position) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_relative_layout, fragment).commit();
            leftMenuTitlesListView.setItemChecked(position, true);
            mainDrawerLayout.closeDrawer(leftMenuLinearLayout);
        } else {
            Log.e(this.getClass().getName(), "Error. Fragment is not created");
        }
    }

}