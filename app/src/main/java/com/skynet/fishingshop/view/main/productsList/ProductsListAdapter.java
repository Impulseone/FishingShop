package com.skynet.fishingshop.view.main.productsList;

import android.app.Activity;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skynet.fishingshop.R;
import com.skynet.fishingshop.view.main.ProductActivity;

import java.util.List;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ProductTilesRow> {

    private final List<Pair<String, String>> data;
    private final Activity activity;

    public ProductsListAdapter(List<Pair<String, String>> data, Activity activity) {
        this.data = data;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ProductTilesRow onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductTilesRow(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.products_tile_row, parent, false), activity);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductTilesRow holder, int position) {
        holder.setText(data.get(position), position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ProductTilesRow extends RecyclerView.ViewHolder {

        public ProductTilesRow(@NonNull View itemView, Activity activity) {
            super(itemView);
            itemView.setOnClickListener(view -> activity.startActivity(new Intent(activity, ProductActivity.class)));
        }

        public void setText(Pair<String, String> text, int position) {
            ((TextView) itemView.findViewById(R.id.first).findViewById(R.id.product_name)).setText(text.first);
            ((TextView) itemView.findViewById(R.id.second).findViewById(R.id.product_name)).setText(text.second);
        }
    }
}
