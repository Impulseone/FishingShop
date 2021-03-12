package com.skynet.fishingshop.view.main.cart;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.skynet.fishingshop.App;
import com.skynet.fishingshop.R;
import com.skynet.fishingshop.db.CartProduct;

import java.util.List;

public class CartProductTileView extends RecyclerView.ViewHolder {

    private CartProduct cartProduct;
    private final CartProductsAdapter adapter;
    private final List<CartProduct> products;
    private int pos;

    public CartProductTileView(@NonNull View itemView, List<CartProduct> products, CartProductsAdapter adapter) {
        super(itemView);
        this.products = products;
        this.adapter = adapter;
    }

    public void setView(CartProduct cartProduct, int pos) {
        this.cartProduct = cartProduct;
        this.pos = pos;
        setImage();
        setPrice();
        setProductName();
        setProductDescription();
        setProductCount();
        setDeleteButton();
        setChangeCountButtons();
    }

    private void setImage() {
        ImageView imageView = itemView.findViewById(R.id.product_image);
        Glide.with(itemView.getContext()).load(cartProduct.imagePath).into(imageView);
    }

    private void setPrice() {
        ((TextView) itemView.findViewById(R.id.product_price)).setText(cartProduct.price + " руб.");
    }

    private void setProductName() {
        ((TextView) itemView.findViewById(R.id.product_name)).setText(cartProduct.name);
    }

    private void setProductDescription() {
        ((TextView) itemView.findViewById(R.id.product_description)).setText(cartProduct.description);
    }

    private void setProductCount() {
        ((TextView) itemView.findViewById(R.id.product_count)).setText(cartProduct.count + " шт.");
    }

    private void setDeleteButton() {
        itemView.findViewById(R.id.delete_button).setOnClickListener(view -> {
            new DeleteTask(adapter, products).execute(cartProduct);
        });
    }

    private void setChangeCountButtons() {
        final int[] count = new int[1];

        itemView.findViewById(R.id.increase_count).setOnClickListener(view -> {
            count[0] = 1;
            new ChangeCountTask(count[0], adapter, cartProduct, pos).execute(cartProduct);
        });

        itemView.findViewById(R.id.decrease_count).setOnClickListener(view -> {
            count[0] = -1;
            new ChangeCountTask(count[0], adapter, cartProduct, pos).execute(cartProduct);
        });
    }

    static class DeleteTask extends AsyncTask<CartProduct, Void, Void> {

        private final CartProductsAdapter adapter;
        private final List<CartProduct> products;

        public DeleteTask(CartProductsAdapter adapter, List<CartProduct> products) {
            this.adapter = adapter;
            this.products = products;
        }

        @Override
        protected Void doInBackground(CartProduct... cartProducts) {
            App.getInstance().getDatabase().productDao().delete(cartProducts[0]);
            products.remove(cartProducts[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.notifyDataSetChanged();
        }
    }

    static class ChangeCountTask extends AsyncTask<CartProduct, Void, Void> {

        private int count;
        private int pos;
        private final CartProductsAdapter adapter;
        private final CartProduct cartProduct;

        public ChangeCountTask(int count, CartProductsAdapter adapter, CartProduct cartProduct, int pos) {
            this.count = count;
            this.adapter = adapter;
            this.pos = pos;
            this.cartProduct = cartProduct;
        }

        @Override
        protected Void doInBackground(CartProduct... cartProducts) {
            cartProduct.count = cartProduct.count + count;
            App.getInstance().getDatabase().productDao().update(cartProduct);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.notifyItemChanged(pos, cartProduct);
        }
    }
}
