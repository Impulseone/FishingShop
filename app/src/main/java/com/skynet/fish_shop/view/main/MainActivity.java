package com.skynet.fish_shop.view.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skynet.fish_shop.App;
import com.skynet.fish_shop.R;
import com.skynet.fish_shop.db.CartProduct;
import com.skynet.fish_shop.db.User;
import com.skynet.fish_shop.extension.CategoriesKeeper;
import com.skynet.fish_shop.model.Category;
import com.skynet.fish_shop.model.CategoryIcon;
import com.skynet.fish_shop.model.DeliveryData;
import com.skynet.fish_shop.model.OrderKeeper;
import com.skynet.fish_shop.model.Product;
import com.skynet.fish_shop.view.authorization.SplashScreenActivity;
import com.skynet.fish_shop.view.extension.LeftNavigationArrayAdapter;
import com.skynet.fish_shop.view.main.cart.CartFragment;
import com.skynet.fish_shop.view.main.catalog.CatalogFragment;
import com.skynet.fish_shop.view.main.favorites.FavoritesFragment;
import com.skynet.fish_shop.view.main.home.HomeFragment;
import com.skynet.fish_shop.view.main.profile.ProfileFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    private String[] leftMenuTitlesArray;
    private DrawerLayout mainDrawerLayout;
    private ListView leftMenuTitlesListView;
    private LinearLayout leftMenuLinearLayout;

    private ActionBarDrawerToggle mDrawerToggle;
    private BottomNavigationView bottomNavigationView;

    private DatabaseReference categoriesFromDbReference;
    private DatabaseReference categoriesIconsFromDbReference;

    private HomeFragment homeFragment;
    private CatalogFragment catalogFragment;

    private BillingProcessor bp;

    private List<CartProduct> cartProducts = new ArrayList<>();

    private DatabaseReference ordersReference;
    OrderKeeper orderKeeper = OrderKeeper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bp = new BillingProcessor(this, null, this);
        bp.initialize();
        if (!bp.isSubscribed("sub_1")) startSubscriptionActivity();

        categoriesFromDbReference = FirebaseDatabase.getInstance().getReference("Категории");
        categoriesIconsFromDbReference = FirebaseDatabase.getInstance().getReference("Иконки категорий");
        ordersReference = FirebaseDatabase.getInstance().getReference("Заказы");

        homeFragment = new HomeFragment();
        catalogFragment = new CatalogFragment();

        setCategoriesListener();
        setCategoriesIconsListener();
        createToolbar();
        createLeftNavigationMenu();
        createHomeFragment();
        createBottomNavigationView();
    }

    private void startSubscriptionActivity() {
        Intent intent = new Intent(this, SubscriptionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public BillingProcessor getBp() {
        return bp;
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
        ft.replace(R.id.main_relative_layout, homeFragment);
        ft.commit();
    }

    private void createHomeFragmentFromCart() {
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.getMenu().setGroupCheckable(0, false, true);
        bottomNavigationView.setSelectedItemId(R.id.action_main);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        ft.replace(R.id.main_relative_layout, homeFragment);
        ft.commit();
    }

    private void createCatalogFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
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

    private void openSplashScreenActivity() {
        FirebaseAuth.getInstance().signOut();
        new ClearTablesTask().execute();
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
        ft.replace(R.id.main_relative_layout, homeFragment);
        ft.commit();
    }

    private void setCategoriesListener() {
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
                homeFragment.update();
                catalogFragment.update();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getMessage());
                System.out.println(error.getDetails());
            }
        });
    }

    private void setCategoriesIconsListener() {
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

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        Toast.makeText(this, "Заказ оформлен", Toast.LENGTH_SHORT).show();
        bp.consumePurchase(productId);
        CartProduct cartProduct = cartProducts.get(0);
        if (cartProduct.count > 1) {
            cartProduct.count = cartProduct.count - 1;
        } else this.cartProducts.remove(cartProduct);
        if (cartProducts.size() > 0) {
            purchase(cartProducts);
        } else {
            ordersReference.updateChildren(createOrder());
            orderKeeper.clear();
            createHomeFragmentFromCart();
        }
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        Toast.makeText(this, "Ошибка (" + errorCode + ")", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }

    public void purchase(List<CartProduct> products) {
        this.cartProducts = checkProductsCount(products);
        CartProduct cartProduct = this.cartProducts.get(0);
        String productId = cartProduct.id;
        bp.purchase(this, "2_1");
    }

    private Map<String, Object> createOrder() {
        Map<String, Object> order = new HashMap<>();
        orderKeeper.setOrderId((new SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.getDefault()).format(Calendar.getInstance().getTime())));
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

    private List<CartProduct> checkProductsCount(List<CartProduct> productsAll) {
        List<CartProduct> products = new ArrayList<>();
        for (CartProduct cartProduct : productsAll) {
            if (cartProduct.count > 0) products.add(cartProduct);
        }
        return products;
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

    static class ClearTablesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            App.getInstance().getDatabase().userDao().clearTable();
            App.getInstance().getDatabase().cartProductDao().clearTable();
            App.getInstance().getDatabase().favoritesProductDao().clearTable();
            return null;
        }
    }

}