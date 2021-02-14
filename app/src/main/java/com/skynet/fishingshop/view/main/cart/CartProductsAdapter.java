package com.skynet.fishingshop.view.main.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.skynet.fishingshop.R;

import org.jetbrains.annotations.NotNull;

public class CartProductsAdapter extends RecyclerView.Adapter<CartProductsAdapter.CartProductView> {

    @NotNull
    @Override
    public CartProductView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_product_tile_view, parent, false);
        return new CartProductView(view);
    }

    @Override
    public void onBindViewHolder(final CartProductView holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class CartProductView extends RecyclerView.ViewHolder {

        public CartProductView(View view) {
            super(view);
        }
    }
}