package com.skynet.fishingshop.view.main.unique_offers;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.skynet.fishingshop.R;
import com.skynet.fishingshop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductTilesRow> {
    private final List<Product> allProducts;
    private List<Pair<Product, Product>> data;
    private final String categoryName;
    private final Fragment fragment;

    public ProductsAdapter(Fragment fragment, List<Product> allProducts, String categoryName) {
        this.categoryName = categoryName;
        this.fragment = fragment;
        this.allProducts = allProducts;
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
                .inflate(R.layout.products_tile_row, parent, false), allProducts, categoryName);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ProductTilesRow holder, int position) {
        holder.setView(data.get(position), fragment);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ProductTilesRow extends RecyclerView.ViewHolder {

        private final List<Product> productList;
        private final String categoryName;

        public ProductTilesRow(@NonNull View itemView, List<Product> productList, String categoryName) {
            super(itemView);
            this.productList = productList;
            this.categoryName = categoryName;
        }

        public void setView(Pair<Product, Product> productPair, Fragment fragment) {
            setFirstProduct(productPair, fragment);
            if (productPair.second != null) setSecondProduct(productPair, fragment);
            else itemView.findViewById(R.id.second).setVisibility(View.GONE);
        }

        private void setFirstProduct(Pair<Product, Product> productPair, Fragment fragment) {
            new ProductTileView(itemView, productPair.first, fragment, productList, categoryName).setView(R.id.first);
        }

        private void setSecondProduct(Pair<Product, Product> productPair, Fragment fragment) {
            new ProductTileView(itemView, productPair.second, fragment, productList, categoryName).setView(R.id.second);
        }
    }
}
