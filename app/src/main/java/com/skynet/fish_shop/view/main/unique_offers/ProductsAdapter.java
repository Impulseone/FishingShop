package com.skynet.fish_shop.view.main.unique_offers;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.skynet.fish_shop.R;
import com.skynet.fish_shop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductTilesRow> {
    private final List<Product> allProducts;
    private List<Pair<Product, Product>> data;
    private final Fragment fragment;
    private final String searchPhrase;
    private final String categoryName;

    public ProductsAdapter(Fragment fragment, List<Product> allProducts, String searchPhrase, String categoryName) {
        this.fragment = fragment;
        this.allProducts = allProducts;
        this.searchPhrase = searchPhrase;
        this.categoryName = categoryName;
        setData();
    }

    private void setData() {
        data = new ArrayList<>();
        for (int i = 0; i < allProducts.size(); i = i + 2) {
            if (i != allProducts.size() - 1)
                data.add(new Pair<>(allProducts.get(i), allProducts.get(i + 1)));
            else data.add(new Pair<>(allProducts.get(i), null));
        }
    }

    @NonNull
    @Override
    public ProductsAdapter.ProductTilesRow onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductsAdapter.ProductTilesRow(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.products_tile_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ProductTilesRow holder, int position) {
        holder.setView(data.get(position), fragment, searchPhrase, position, categoryName, allProducts);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ProductTilesRow extends RecyclerView.ViewHolder {

        private String categoryName;
        private List<Product> products;

        public ProductTilesRow(@NonNull View itemView) {
            super(itemView);
        }

        public void setView(Pair<Product, Product> productPair, Fragment fragment, String searchPhrase, int scrollPosition, String categoryName, List<Product> products) {
            this.categoryName = categoryName;
            this.products = products;
            setFirstProduct(productPair, fragment, searchPhrase, scrollPosition);
            if (productPair.second != null)
                setSecondProduct(productPair, fragment, searchPhrase, scrollPosition);
            else itemView.findViewById(R.id.second).setVisibility(View.GONE);
        }

        private void setFirstProduct(Pair<Product, Product> productPair, Fragment fragment, String searchPhrase, int scrollPosition) {
            new ProductTileView(itemView, productPair.first, fragment, searchPhrase, scrollPosition, categoryName, products).setView(R.id.first);
        }

        private void setSecondProduct(Pair<Product, Product> productPair, Fragment fragment, String searchPhrase, int scrollPosition) {
            new ProductTileView(itemView, productPair.second, fragment, searchPhrase, scrollPosition, categoryName, products).setView(R.id.second);
        }
    }
}
