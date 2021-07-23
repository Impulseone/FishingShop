package com.skynet.fish_shop.view.main.product;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.skynet.fish_shop.App;
import com.skynet.fish_shop.R;
import com.skynet.fish_shop.db.AppDatabase;
import com.skynet.fish_shop.db.CartProduct;
import com.skynet.fish_shop.db.FavoritesProduct;
import com.skynet.fish_shop.model.Category;
import com.skynet.fish_shop.model.Product;
import com.skynet.fish_shop.view.extension.ImageDialogView;
import com.skynet.fish_shop.view.main.productsList.ProductsListForCategoryFragment;
import com.skynet.fish_shop.view.main.productsList.SearchedProductsListFragment;
import com.squareup.picasso.Picasso;

import static com.skynet.fish_shop.view.main.home.HomeFragment.hideKeyboard;

public class ProductForCategoryFragment extends Fragment {

    private final Category category;
    private final Product product;
    private final int scrollPosition;
    boolean isMove;

    public ProductForCategoryFragment(Category category, Product product, int scrollPosition) {
        this.category = category;
        this.product = product;
        this.scrollPosition = scrollPosition;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_for_category, container, false);
        isMove = false;
        setBackButton(view);
        setBottomNavigationVisibility(View.GONE);
        setSearchField(view);
        setTitle(view);
        setProductDescription(view);
        setPrice(view);
        setImages(view);
        setAddToCartButton(view);
        setAddToFavoritesButton(view);
        return view;
    }

    private void setSearchField(View view) {
        EditText editText = (EditText) view.findViewById(R.id.search_product);
        editText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE && !textView.getText().toString().isEmpty()) {
                setBottomNavigationVisibility(View.VISIBLE);
                createSearchedProductsListFragment(editText.getText().toString());
            }
            return true;
        });
        view.findViewById(R.id.search_button).setOnClickListener(view1 -> {
            if (!editText.getText().toString().isEmpty()) {
                createSearchedProductsListFragment(editText.getText().toString());
                ((BottomNavigationView) ProductForCategoryFragment.this.getActivity().findViewById(R.id.bottom_navigation)).getMenu().setGroupCheckable(0, false, true);
            } else hideKeyboard(ProductForCategoryFragment.this.getActivity());
        });
    }

    private void createSearchedProductsListFragment(String search) {
        SearchedProductsListFragment searchedProductsListFragment = new SearchedProductsListFragment(search, scrollPosition);
        FragmentTransaction ft = this.getParentFragmentManager().beginTransaction();
        ft.replace(R.id.main_relative_layout, searchedProductsListFragment);
        ft.commit();
        hideKeyboard(getActivity());
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

    private void setBottomNavigationVisibility(int visibility) {
        this.getActivity().findViewById(R.id.bottom_navigation).setVisibility(visibility);
    }

    private void setPrice(View view) {
        ((TextView) view.findViewById(R.id.price)).setText(product.price + " руб.");
    }

    private void setImages(View view) {
        LinearLayout imagesLinearLayout = view.findViewById(R.id.product_images_linear_layout);
        String[] imagesPaths = product.imagePath.split("; ");
        for (String path : imagesPaths) {
            ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(250, 250);
            layoutParams.setMargins(10,0,10,0);
            imageView.setLayoutParams(layoutParams);
            imageView.setOnClickListener(view1 -> new ImageDialogView(path).show(getActivity().getSupportFragmentManager(), ""));
            if (path != null && !path.isEmpty()) Picasso.get().load(path).into(imageView);
            imagesLinearLayout.addView(imageView);
        }
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
        ProductsListForCategoryFragment productsListForCategoryFragment = new ProductsListForCategoryFragment(category, scrollPosition);
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