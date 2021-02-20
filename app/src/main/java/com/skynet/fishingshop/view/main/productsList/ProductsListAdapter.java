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
import com.skynet.fishingshop.view.main.product.ProductFragment;

import java.util.List;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ProductTilesRow> {

    private final List<Pair<String, String>> data;
    private final Fragment fragment;

    public ProductsListAdapter(List<Pair<String, String>> data, Fragment fragment) {
        this.data = data;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ProductTilesRow onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductTilesRow(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.products_tile_row, parent, false), fragment);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductTilesRow holder, int position) {
        holder.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ProductTilesRow extends RecyclerView.ViewHolder {

        public ProductTilesRow(@NonNull View itemView, Fragment fragment) {
            super(itemView);
            itemView.findViewById(R.id.first).setOnClickListener(view1 -> {
                FragmentTransaction ft = fragment.getParentFragmentManager().beginTransaction();
                ProductFragment productFragment = new ProductFragment();
                ft.replace(R.id.main_relative_layout, productFragment);
                ft.commit();
            });
            itemView.findViewById(R.id.second).setOnClickListener(view1 -> {
                FragmentTransaction ft = fragment.getParentFragmentManager().beginTransaction();
                ProductFragment productFragment = new ProductFragment();
                ft.replace(R.id.main_relative_layout, productFragment);
                ft.commit();
            });
        }

        public void setText(Pair<String, String> text) {
            ((TextView) itemView.findViewById(R.id.first).findViewById(R.id.product_name)).setText(text.first);
            ((TextView) itemView.findViewById(R.id.second).findViewById(R.id.product_name)).setText(text.second);
        }
    }
}
