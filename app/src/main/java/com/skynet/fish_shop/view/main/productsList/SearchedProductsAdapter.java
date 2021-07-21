package com.skynet.fish_shop.view.main.productsList;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.skynet.fish_shop.R;
import com.skynet.fish_shop.extension.CategoriesKeeper;
import com.skynet.fish_shop.model.Category;
import com.skynet.fish_shop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class SearchedProductsAdapter extends RecyclerView.Adapter<SearchedProductsAdapter.ProductTilesRow> {

    private List<Pair<Product, Product>> data;
    private final Fragment fragment;
    private final String searchPhrase;

    public SearchedProductsAdapter(Fragment fragment, String searchPhrase) {
        this.fragment = fragment;
        this.searchPhrase = searchPhrase;
        setData();
    }

    private void setData() {
        data = new ArrayList<>();
        List<Product> productsList = getAllProducts();
        List<Product> productListFiltered = new ArrayList<>();
        for (Product product : productsList) {
            try {
                if (product.name != null && (product.name.contains(searchPhrase) || product.name.toLowerCase().contains(searchPhrase)))
                    productListFiltered.add(product);
            } catch (Exception e) {
                System.out.println("PRODUCT ID: " + product.id);
                e.printStackTrace();
            }
        }

        for (int i = 0; i < productListFiltered.size(); i = i + 2) {
            if (i != productListFiltered.size() - 1)
                data.add(new Pair<>(productListFiltered.get(i), productListFiltered.get(i + 1)));
            else data.add(new Pair<>(productListFiltered.get(i), null));
        }
    }

    private List<Product> getAllProducts() {
        List<Product> allProducts = new ArrayList<>();
        for (Category category : CategoriesKeeper.getInstance().getCategories()) {
            allProducts.addAll(category.getProductList());
        }
        return allProducts;
    }

    @NonNull
    @Override
    public ProductTilesRow onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductTilesRow(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.products_tile_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductTilesRow holder, int position) {
        holder.setView(data.get(position), fragment, searchPhrase);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ProductTilesRow extends RecyclerView.ViewHolder {

        public ProductTilesRow(@NonNull View itemView) {
            super(itemView);
        }

        public void setView(Pair<Product, Product> productPair, Fragment fragment, String searchPhrase) {
            setFirstProduct(productPair, fragment, searchPhrase);
            if (productPair.second != null) setSecondProduct(productPair, fragment, searchPhrase);
            else itemView.findViewById(R.id.second).setVisibility(View.GONE);
        }

        private void setFirstProduct(Pair<Product, Product> productPair, Fragment fragment, String searchPhrase) {
            new ProductTileView(itemView, productPair.first, fragment, null, searchPhrase).setView(R.id.first);
        }

        private void setSecondProduct(Pair<Product, Product> productPair, Fragment fragment, String searchPhrase) {
            new ProductTileView(itemView, productPair.second, fragment, null, searchPhrase).setView(R.id.second);
        }
    }
}
