package com.skynet.fishingshop.view.main.product;

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
import com.google.android.material.snackbar.Snackbar;
import com.skynet.fishingshop.App;
import com.skynet.fishingshop.R;
import com.skynet.fishingshop.db.AppDatabase;
import com.skynet.fishingshop.db.CartProduct;
import com.skynet.fishingshop.model.Category;
import com.skynet.fishingshop.model.Product;
import com.skynet.fishingshop.view.main.productsList.ProductsListFragment;

public class ProductFragment extends Fragment {

    private final Category category;
    private final Product product;

    public ProductFragment(Category category, Product product) {
        this.category = category;
        this.product = product;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product, container, false);
        setBackButton(view);
        setBottomNavigationVisibility();
        setTitle(view);
        setProductDescription(view);
        setPrice(view);
        setImage(view);
        setAddToCartButton(view);
        return view;
    }

    private void setTitle(View view) {
        ((TextView) view.findViewById(R.id.product_name)).setText(product.name.substring(2));
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
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddProductToCartTask(product, view.getContext()).execute();
            }
        });
    }

    private void back() {
        this.getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ProductsListFragment productsListFragment = new ProductsListFragment(category);
        ft.replace(R.id.main_relative_layout, productsListFragment);
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
            CartProduct existing = appDatabase.productDao().getById(cartProduct.id);
            if (existing != null) {
                cartProduct.count = cartProduct.count + 1;
                appDatabase.productDao().update(cartProduct);
            } else
                appDatabase.productDao().insert(cartProduct);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(context, "Товар добавлен в корзину", Toast.LENGTH_SHORT).show();
        }
    }
}