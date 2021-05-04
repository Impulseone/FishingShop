package com.skynet.fishingshop.view.main.unique_offers;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.skynet.fishingshop.App;
import com.skynet.fishingshop.R;
import com.skynet.fishingshop.db.AppDatabase;
import com.skynet.fishingshop.db.CartProduct;
import com.skynet.fishingshop.db.FavoritesProduct;
import com.skynet.fishingshop.model.Category;
import com.skynet.fishingshop.model.Product;
import com.skynet.fishingshop.view.main.home.HomeFragment;
import com.skynet.fishingshop.view.main.productsList.ProductsListForCategoryFragment;
import com.skynet.fishingshop.view.main.productsList.SearchedProductsListFragment;

public class ProductFragment extends Fragment {

    private final Product product;
    private String searchPhrase;
    private final Category category;

    public ProductFragment(Product product, String searchPhrase, Category category) {
        this.product = product;
        this.searchPhrase = searchPhrase;
        this.category = category;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_for_category, container, false);
        setBackButton(view);
        setBottomNavigationVisibility();
        setTitle(view);
        setProductDescription(view);
        setPrice(view);
        setImage(view);
        setAddToCartButton(view);
        setAddToFavoritesButton(view);
        return view;
    }

    private void setTitle(View view) {
        ((TextView) view.findViewById(R.id.product_name)).setText(product.name);
    }

    private void setProductDescription(View view) {
        ((TextView) view.findViewById(R.id.product_description)).setText(product.description);
    }

    private void setBackButton(View view) {
        View backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(view1 -> back());
    }

    private void setBottomNavigationVisibility() {
        this.getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
    }

    private void setPrice(View view) {
        ((TextView) view.findViewById(R.id.price)).setText(product.price + " руб.");
    }

    private void setImage(View view) {
        ImageView imageView = view.findViewById(R.id.product_image);
        Glide.with(this).load(product.imagePath).into(imageView);
    }

    private void setAddToCartButton(View view) {
        View addToCartButton = view.findViewById(R.id.add_to_cart_button);
        addToCartButton.setOnClickListener(view1 -> new ProductFragment.AddProductToCartTask(product, view1.getContext()).execute());
    }

    private void setAddToFavoritesButton(View view) {
        View addToCartButton = view.findViewById(R.id.add_to_favorites_button);
        addToCartButton.setOnClickListener(view1 -> new ProductFragment.AddProductToFavoritesTask(product, view1.getContext()).execute());
    }

    private void back() {
        this.getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        if (searchPhrase == null && category == null) {
            HomeFragment homeFragment = new HomeFragment();
            ft.replace(R.id.main_relative_layout, homeFragment);
        } else if (searchPhrase != null) {
            SearchedProductsListFragment searchedProductsListFragment = new SearchedProductsListFragment(searchPhrase);
            ft.replace(R.id.main_relative_layout, searchedProductsListFragment);
        } else {
            ProductsListForCategoryFragment productsListForCategoryFragment = new ProductsListForCategoryFragment(category);
            ft.replace(R.id.main_relative_layout, productsListForCategoryFragment);
        }
        ft.commit();


    }

    static class AddProductToCartTask extends AsyncTask<Void, Void, Void> {

        private final Context context;
        private final Product product;

        public AddProductToCartTask(Product product, Context context) {
            this.product = product;
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase appDatabase = App.getInstance().getDatabase();
            CartProduct cartProduct = new CartProduct(product);
            CartProduct existing = appDatabase.cartProductDao().getById(cartProduct.id);
            if (existing != null) {
                cartProduct.count = cartProduct.count + 1;
                appDatabase.cartProductDao().update(cartProduct);
            } else
                appDatabase.cartProductDao().insert(cartProduct);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(context, "Товар добавлен в корзину", Toast.LENGTH_SHORT).show();
        }
    }

    static class AddProductToFavoritesTask extends AsyncTask<Void, Void, Void> {

        private final Context context;
        private final Product product;

        public AddProductToFavoritesTask(Product product, Context context) {
            this.product = product;
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase appDatabase = App.getInstance().getDatabase();
            FavoritesProduct favoritesProduct = new FavoritesProduct(product);
            appDatabase.favoritesProductDao().insert(favoritesProduct);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(context, "Товар добавлен в избранное", Toast.LENGTH_SHORT).show();
        }
    }
}