package com.skynet.fish_shop.view.main.unique_offers;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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
import com.skynet.fish_shop.view.extension.ImageDialogView;
import com.skynet.fish_shop.view.main.home.HomeFragment;
import com.skynet.fish_shop.view.main.productsList.ProductsListForCategoryFragment;
import com.skynet.fish_shop.view.main.productsList.SearchedProductsListFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.skynet.fish_shop.view.main.home.HomeFragment.hideKeyboard;

public class ProductFragment extends Fragment {

    private final Product product;
    private final String searchPhrase;
    private final String categoryName;
    private final int scrollPosition;
    private final List<Product> allProducts;

    public ProductFragment(Product product, String searchPhrase, int scrollPosition, List<Product> allProducts, String categoryName) {
        this.product = product;
        this.searchPhrase = searchPhrase;
        this.scrollPosition = scrollPosition;
        this.allProducts = allProducts;
        this.categoryName = categoryName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_for_category, container, false);
        setBackButton(view);
        setBottomNavigationVisibility(View.GONE);
        setSearchField(view);
        setTitle(view);
        setProductDescription(view);
        setPrice(view);
        setImage(view);
        setAddToCartButton(view);
        setAddToFavoritesButton(view);
        return view;
    }

    private void setSearchField(View view) {
        ((EditText) view.findViewById(R.id.search_product)).setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE && !textView.getText().toString().isEmpty()) {
                setBottomNavigationVisibility(View.VISIBLE);
                createSearchedProductsListFragment(textView.getText().toString());
            }
            return true;
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

    private void setImage(View view) {
        ImageView imageView = view.findViewById(R.id.product_image);
        String path = product.imagePath;
        if (path != null && !path.isEmpty()) Picasso.get().load(path).into(imageView);
        imageView.setOnClickListener(view1 -> new ImageDialogView(path).show(getActivity().getSupportFragmentManager(), ""));
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
        if (searchPhrase == null) {
            UniqueOfferFragment uniqueOfferFragment = new UniqueOfferFragment(allProducts, categoryName, scrollPosition);
            ft.replace(R.id.main_relative_layout, uniqueOfferFragment);
        } else {
            SearchedProductsListFragment searchedProductsListFragment = new SearchedProductsListFragment(searchPhrase, scrollPosition);
            ft.replace(R.id.main_relative_layout, searchedProductsListFragment);
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