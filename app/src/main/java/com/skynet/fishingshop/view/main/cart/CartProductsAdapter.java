package com.skynet.fishingshop.view.main.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.skynet.fishingshop.R;
import com.skynet.fishingshop.view.main.orderConfirmation.OrderConfirmationFragment;
import com.skynet.fishingshop.view.main.productsList.ProductsListFragment;

import org.jetbrains.annotations.NotNull;

public class CartProductsAdapter extends RecyclerView.Adapter<CartProductsAdapter.CartProductView> {

   private final Fragment fragment;

    public CartProductsAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @NotNull
    @Override
    public CartProductView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_product_tile_view, parent, false);
        return new CartProductView(view, fragment);
    }

    @Override
    public void onBindViewHolder(final CartProductView holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class CartProductView extends RecyclerView.ViewHolder {

        public CartProductView(View view, Fragment fragment) {
            super(view);
        }
    }
}