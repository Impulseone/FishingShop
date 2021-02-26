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
import com.skynet.fishingshop.model.Product;
import com.skynet.fishingshop.view.main.product.ProductFragment;

import java.util.ArrayList;
import java.util.List;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ProductTilesRow> {

    private final List<Pair<Product, Product>> data;
    private final List<Product> productsList;
    private final Fragment fragment;

    public ProductsListAdapter(List<Product> productsList, Fragment fragment) {
        this.productsList = productsList;
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
        holder.setView(data.get(position), fragment, productsList);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ProductTilesRow extends RecyclerView.ViewHolder {

        private List<Product> all;

        public ProductTilesRow(@NonNull View itemView) {
            super(itemView);
        }

        public void setView(Pair<Product, Product> productPair, Fragment fragment, List<Product> all) {
            this.all = all;
            setFirstProduct(productPair, fragment);
            if (productPair.second != null) setSecondProduct(productPair, fragment);
            else itemView.findViewById(R.id.second).setVisibility(View.GONE);
        }

        private void setFirstProduct(Pair<Product, Product> productPair, Fragment fragment) {
            ((TextView) itemView.findViewById(R.id.first).findViewById(R.id.product_name)).setText(productPair.first.name);
            itemView.findViewById(R.id.first).setOnClickListener(view1 -> {
                FragmentTransaction ft = fragment.getParentFragmentManager().beginTransaction();
                ProductFragment productFragment = new ProductFragment(all);
                ft.replace(R.id.main_relative_layout, productFragment);
                ft.commit();
            });
        }

        private void setSecondProduct(Pair<Product, Product> productPair, Fragment fragment) {
            ((TextView) itemView.findViewById(R.id.second).findViewById(R.id.product_name)).setText(productPair.second.name);
            itemView.findViewById(R.id.second).setOnClickListener(view1 -> {
                FragmentTransaction ft = fragment.getParentFragmentManager().beginTransaction();
                ProductFragment productFragment = new ProductFragment(all);
                ft.replace(R.id.main_relative_layout, productFragment);
                ft.commit();
            });
        }
    }
}
