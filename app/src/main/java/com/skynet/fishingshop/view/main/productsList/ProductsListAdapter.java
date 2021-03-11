package com.skynet.fishingshop.view.main.productsList;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.skynet.fishingshop.R;
import com.skynet.fishingshop.model.Category;
import com.skynet.fishingshop.model.Product;
import com.skynet.fishingshop.view.main.product.ProductFragment;

import java.util.ArrayList;
import java.util.List;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ProductTilesRow> {

    private final List<Pair<Product, Product>> data;
    private final List<Product> productsList;
    private final Fragment fragment;
    private final Category category;

    public ProductsListAdapter(Category category, Fragment fragment) {
        this.category = category;
        this.productsList = category.getProductList();
        data = new ArrayList<>();
        for (int i = 0; i < productsList.size(); i = i + 2) {
            if (i != productsList.size() - 1)
                data.add(new Pair<>(productsList.get(i), productsList.get(i + 1)));
            else data.add(new Pair<>(productsList.get(i), null));
        }
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ProductTilesRow onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductTilesRow(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.products_tile_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductTilesRow holder, int position) {
        holder.setView(data.get(position), fragment, category);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ProductTilesRow extends RecyclerView.ViewHolder {

        private Category category;

        public ProductTilesRow(@NonNull View itemView) {
            super(itemView);
        }

        public void setView(Pair<Product, Product> productPair, Fragment fragment, Category category) {
            this.category = category;
            setFirstProduct(productPair, fragment);
            if (productPair.second != null) setSecondProduct(productPair, fragment);
            else itemView.findViewById(R.id.second).setVisibility(View.GONE);
        }

        private void setFirstProduct(Pair<Product, Product> productPair, Fragment fragment) {
            new ProductTileView(itemView, productPair.first, fragment, category).setView();
        }

        private void setSecondProduct(Pair<Product, Product> productPair, Fragment fragment) {
            new ProductTileView(itemView, productPair.second, fragment, category).setView();
        }
    }
}
