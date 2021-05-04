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
    private final Fragment fragment;
    private final String searchPhrase;

    public ProductsAdapter(Fragment fragment, List<Product> allProducts, String searchPhrase) {
        this.fragment = fragment;
        this.allProducts = allProducts;
        this.searchPhrase = searchPhrase;
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
            new ProductTileView(itemView, productPair.first, fragment, searchPhrase).setView(R.id.first);
        }

        private void setSecondProduct(Pair<Product, Product> productPair, Fragment fragment, String searchPhrase) {
            new ProductTileView(itemView, productPair.second, fragment, searchPhrase).setView(R.id.second);
        }
    }
}
