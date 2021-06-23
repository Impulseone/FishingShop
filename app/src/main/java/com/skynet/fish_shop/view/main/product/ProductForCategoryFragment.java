package com.skynet.fish_shop.view.main.product;

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
import com.skynet.fish_shop.App;
import com.skynet.fish_shop.R;
import com.skynet.fish_shop.db.AppDatabase;
import com.skynet.fish_shop.db.CartProduct;
import com.skynet.fish_shop.db.FavoritesProduct;
import com.skynet.fish_shop.model.Category;
import com.skynet.fish_shop.model.Product;
import com.skynet.fish_shop.view.main.productsList.ProductsListForCategoryFragment;
import com.squareup.picasso.Picasso;

public class ProductForCategoryFragment extends Fragment {

    private final Category category;
    private final Product product;

    public ProductForCategoryFragment(Category category, Product product) {
        this.category = category;
        this.product = product;
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
        String path = product.imagePath;
        if (path != null && !path.isEmpty()) Picasso.get().load(path).into(imageView);
    }

    private void setAddToCartButton(View view) {
        View addToCartButton = view.findViewById(R.id.add_to_cart_button);
        addToCartButton.setOnClickListener(view1 -> new AddProductToCartTask(product, view1.getContext()).execute());
    }

    private void setAddToFavoritesButton(View view) {
        View addToCartButton = view.findViewById(R.id.add_to_favorites_button);
        addToCartButton.setOnClickListener(view1 -> new AddProductToFavoritesTask(product, view1.getContext()).execute());
    }

    private void back() {
        this.getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ProductsListForCategoryFragment productsListForCategoryFragment = new ProductsListForCategoryFragment(category);
        ft.replace(R.id.main_relative_layout, productsListForCategoryFragment);
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