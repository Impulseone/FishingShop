package com.skynet.fishingshop.view.main.productsList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skynet.fishingshop.R;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ProductTilesRow> {

    @NonNull
    @Override
    public ProductTilesRow onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductTilesRow(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.products_tile_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductTilesRow holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class ProductTilesRow extends RecyclerView.ViewHolder {

        public ProductTilesRow(@NonNull View itemView) {
            super(itemView);
        }
    }
}
